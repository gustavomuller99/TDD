class TestCase:
    def __init__(self, name):
        self.name = name

    def run(self, result):
        result.testStarted()

        try:
            self.setUp()
        except Exception:
            result.testFailed()
            return

        try:
            method = getattr(self, self.name)
            method()
        except Exception:
            result.testFailed()
            result.addFailed(self.name)
            
        self.tearDown()

    def setUp(self):
        pass

    def tearDown(self):
        pass

class TestSuite:
    def __init__(self):
        self.tests = []

    def add(self, test):
        self.tests.append(test)

    def run(self, result):
        for test in self.tests:
            test.run(result)

class TestResult:
    def __init__(self):
        self.runCount = 0
        self.errorCount = 0
        self.log = ""

    def testStarted(self):
        self.runCount = self.runCount + 1

    def testFailed(self):
        self.errorCount = self.errorCount + 1

    def addFailed(self, message):
        self.log = self.log + "Failed: " + message + "\n"

    def summary(self):
        return "%d run, %d failed" % (self.runCount, self.errorCount)
    
    def getLog(self):
        return self.log


