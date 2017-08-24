# Selenium
Sample KDT Selenium web test automation framework:
* test cases are to be defined within .xls file (one can add a keyword and up to 3 parameters). Once added this needs to be coded at KeywordActions (note that substring match is used so
it is important to name any new keywords/methods in the way that they are not a substring to any existing keywords/methods already implemented)
* XLSTestCaseReader is responsible for reading test data from excel test case file
* KeywordActions is the main library for keyword methods
* KeywordRouter is responsible for matching keywords from test case file to methods in KeywordActions
* KeywordRunner is responsible for running the tests
* I didn't implement any pageobject class here as the demo works with just one page, so I'm operating with raw web elements retrieved through driver calls. Pageobjects
describing the page and elements on it can be implemented in future if the need arises
* This small framework lacks some features, like Run flag capability allowing to choose keywords to run/skip in test case file.

This is for demo purpose only and the test case is exclusively implemented for interview purposes
