iPeer Building Block for Blackboard Learn
============================================

This building block provides tight integration for iPeer to Blackboard Learn

Features:
* Creating courses
* Roaster sync
* Group sync
* and more

Requirements:
* JDK
* Apache Ant

How to compile:

    git clone https://github.com/ubc/ipeer-b2.git
    cd ipeer-b2
    ant

Follow the screen and answer a few questions,e.g. the deployment address. This will generate a war file. Upload it to Blackboard Learn.

To run the tests:

    ant test

This will run unit test and integration tests. For integration test, you will need to have a Blackboard instance running on http://bblvm.dev (can be changed in functionalTestContext.xml) and iPeer instance running on http://ipeer.dev (can be changed in defaultSettings.properties)
