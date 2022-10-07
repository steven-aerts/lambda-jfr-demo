#/bin/sh
curl -s "$1?jfr" > /dev/null
aws s3 sync s3://devoxx-demo/jfr-dumps/ /tmp/jfr-dumps