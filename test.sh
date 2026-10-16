#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
mkdir -p "$ROOT/build/tests"
javac -d "$ROOT/build/tests" "$ROOT/src/com/jeremykenedy/prismclock/PrismOptions.java" "$ROOT/src/com/jeremykenedy/prismclock/PrismMesh.java" "$ROOT/src/com/jeremykenedy/prismclock/ClockLayout.java" "$ROOT/tests/PrismTest.java"
java -cp "$ROOT/build/tests" com.jeremykenedy.prismclock.PrismTest
