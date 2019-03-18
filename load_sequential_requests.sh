#!/bin/sh

echo "Calling End-point 1000 times"

for i in {1..1000}
do
curl http://localhost:8080/api/users/test_load
done