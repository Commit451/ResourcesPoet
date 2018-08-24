#!/bin/bash
# Tests the project or uploads the created archive to Bintray when tag is present

if [ -n "$TRAVIS_TAG" ]; then

    echo "Building release for tag: $TRAVIS_TAG"

    version=$TRAVIS_TAG
    echo "Publishing version $version"

    # Write the new version in the properties
    printf "\nPOM_VERSION=$version" >> $lib/gradle.properties

    eval "./gradlew build bintrayUpload"
else
     echo "Building project"
     eval "./gradlew test"
fi