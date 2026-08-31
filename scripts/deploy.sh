#!/usr/bin/env bash
#
# Deploy the WattsNext backend to the KIT SCC VM.
# Run from the repository root, inside the KIT network/VPN (SSH is internal only).
# See DEPLOYMENT.md for the manual steps and one-time server setup this automates.
#
# Order is stop -> upload -> start (never `restart` while the jar is overwritten):
# overwriting app.jar under the running JVM makes its graceful shutdown throw
# NoClassDefFoundError against the replaced fat-jar, which can abort persistence
# of in-memory games.
#
# The sudo steps run over `ssh -t` and will prompt once for the selgrad password.

set -euo pipefail

HOST="energy-game.enzo.kit.edu"
JAR="app/build/libs/app-0.0.1-SNAPSHOT.jar"
REMOTE_JAR="~/wattsnext/app.jar"

cd "$(dirname "$0")/.."

# The build needs JDK 21. This uses your ambient JAVA_HOME; to override, set
# JAVA_HOME_21 to a Windows-style JDK 21 path, e.g.
#   JAVA_HOME_21='C:\Program Files\Java\jdk-21' scripts/deploy.sh
if [[ -n "${JAVA_HOME_21:-}" ]]; then
  export JAVA_HOME="$JAVA_HOME_21"
fi

echo "==> Building backend (skipping tests)"
case "$(uname -s)" in
  MINGW*|MSYS*|CYGWIN*)
    # Git Bash on Windows: run the Windows wrapper through cmd, otherwise bash
    # tries to execute the .bat as a shell script. //c becomes /c after MSYS
    # path munging; the quoted command keeps the :task args from being mangled.
    cmd //c "gradlew.bat :app:build -x test"
    ;;
  *)
    ./gradlew :app:build -x test
    ;;
esac

if [[ ! -f "$JAR" ]]; then
  echo "!! Build artifact not found: $JAR" >&2
  exit 1
fi

echo "==> Stopping backend on $HOST (persists game state)"
ssh -t "$HOST" 'sudo systemctl stop wattsnext-backend'

echo "==> Uploading jar"
scp "$JAR" "$HOST:$REMOTE_JAR"

echo "==> Starting backend"
ssh -t "$HOST" 'sudo systemctl start wattsnext-backend'

echo "==> Status"
ssh "$HOST" 'systemctl --no-pager --lines=0 status wattsnext-backend'

echo "==> Health check"
code=$(curl -s -o /dev/null -w '%{http_code}' "https://$HOST/api/game-init" || true)
echo "GET /api/game-init -> HTTP $code (a 4xx means the backend is up and answering)"

echo "==> Done."
