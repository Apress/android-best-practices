## Setup

To start a new Android project:

1. Install the [Android SDK](http://developer.android.com/sdk/index.html). On Mac OS X with [Homebrew](http://brew.sh/) just run:
    ```bash
    brew install android-sdk
    ```

2. Set your `ANDROID_HOME` environment variable to `/usr/local/Cellar/android-sdk/<version>`.

3. Run the Android SDK GUI and install API 18 and any other APIs you might need. You can start the GUI like so:
    ```bash
    android
    ```

4. Use [Maven Android SDK Deployer](https://github.com/mosabua/maven-android-sdk-deployer) to maven-ize the Android SDK:
    ```bash
    git clone https://github.com/mosabua/maven-android-sdk-deployer.git
    (cd maven-android-sdk-deployer && mvn install -P 4.3)
    ```

6. In the project directory you should be able to run the tests:
    ```bash
    cd my-new-project
    mvn clean test
    ```