#!/bin/bash

./gradlew lint > lint-results.txt

# Check if "Multiple substitutions specified" or "StringFormatMatches" exist in the lint results
grep -qE "Multiple substitutions specified|StringFormatMatches" lint-results.txt

GREP_STATUS=$?

if [ $GREP_STATUS -eq 0 ]; then
  echo "Test Failed: Lint issues found (Multiple substitutions or String format match)"
  exit 1
else
  echo "Test Passed: No lint issues found"
  exit 0
fi
