from xunit import *

class WasRun(TestCase):
    def __init__(self, name):
        super().__init__(name)

    def testMethod(self):
        self.log = self.log + "testMethod "

    def testBrokenMethod(self):
        raise Exception

    def setUp(self):
        self.log = "setUp "

    def tearDown(self):
        self.log = self.log + "tearDown "

class WasRunBrokenSetUp(TestCase):
    def __init__(self, name):
        super().__init__(name)

    def testMethod(self):
        self.log = self.log + "testMethod "

    def setUp(self):
        raise Exception

class TestCaseTest(TestCase):
    def __init__(self, name):
        super().__init__(name)

    def setUp(self):
        self.result = TestResult()

    def testTemplateMethod(self):
        self.test = WasRun("testMethod")
        self.test.run(self.result)
        assert("setUp testMethod tearDown " == self.test.log)

    def testResult(self):
        self.test = WasRun("testMethod")
        self.test.run(self.result)
        assert("1 run, 0 failed" == self.result.summary())

    def testFailedResult(self):
        self.test = WasRun("testBrokenMethod")
        self.test.run(self.result)
        assert("1 run, 1 failed" == self.result.summary())

    def testFailedResultFormatting(self):
        self.result.testStarted()
        self.result.testFailed()
        assert("1 run, 1 failed" == self.result.summary())

    def testFailedSetUp(self):
        self.test = WasRunBrokenSetUp("testMethod")
        self.test.run(self.result)
        assert("1 run, 1 failed" == self.result.summary())

    def testSuite(self):
        suite = TestSuite()
        suite.add(WasRun("testMethod"))
        suite.add(WasRun("testBrokenMethod"))
        self.result = TestResult()
        suite.run(self.result)
        assert("2 run, 1 failed" == self.result.summary())

    def testFailedLog(self):
        self.test = WasRun("testBrokenMethod")
        self.test.run(self.result)
        assert("Failed: testBrokenMethod\n" == self.result.log)

suite = TestSuite()
suite.add(TestCaseTest("testTemplateMethod"))
suite.add(TestCaseTest("testResult"))
suite.add(TestCaseTest("testFailedResult"))
suite.add(TestCaseTest("testFailedResultFormatting"))
suite.add(TestCaseTest("testFailedSetUp"))
suite.add(TestCaseTest("testSuite"))
suite.add(TestCaseTest("testFailedLog"))
suite.add(TestCaseTest("inexistentMethod"))  # This will raise an exception

result = TestResult()
suite.run(result)
print(result.summary())
print(result.getLog())