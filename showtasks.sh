#!/usr/bin/env bash

fail() {
  echo 'Error: Something bad happened' >&2
}

end() {
  echo "Done!"
}

run() {
  open http://localhost:8080/crud/v1/task/getTasks
}


if [ "./runcrud.sh" ]; then
  run
  end

else
  fail
fi