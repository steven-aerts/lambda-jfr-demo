# Serverless JFR

> Recording the flight of Serverless Functions  
> Devoxx talk 2022/10/10

Hello and welcome.  I really like that you are showing interest in the aditional
content of the Devoxx talk I preformed on 2022/10/10.

This repository contains the [slides](./slides.pdf) and all code samples.
A recording will later be made available by Devoxx.

The different code samples can be found under the different tags in git.

* `git checkout tag0`: baseline, a basic lambda we will work with
* `git checkout tag1`: enables JFR
* `git checkout tag2`: do more in the beginning of lambda
* `git checkout tag3`: extra optimization tricks (CRT http implementation, hard code was region and cred provider)
* `git checkout tag4`: enable JFR without code changes with lambda layer without

To deploy the code in your own environment:

```sh
cd lambda
../gradlew assemble && sam build && sam deploy
```

After deployment, sam will show you a url from the format
`https://<id>.lambda-url.<region>.on.aws/` everyone can trigger your lambda:

```sh
curl https://<id>.lambda-url.<region>.on.aws/
```

There are also 2 helper scripts:

```sh
# call lambda 10 times and summarize its duration
./loadTest.sh https://<id>.lambda-url.<region>.on.aws/
# call lambda with ?jfr trigger to dump jfr's and sync them to /tmp/jfr-dumps
./getJfr.sh https://<id>.lambda-url.<region>.on.aws/
```

Enjoy!

Any feedback is welcome.  
Do not feel shy to ask your questions in the form of an issue on this repo or even a PR.
