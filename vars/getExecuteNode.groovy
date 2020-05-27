import groovy.json.JsonOutput;

@NonCPS
def call(String executeJobName, Object parameter = []){
    echo "lib executeJobName: ${executeJobName}";
    echo "lib parameter: ${parameter}" ;
    def testEnvironment = parameter.testEnvironment;
    def builder = new groovy.json.JsonBuilder();
    def configString = """
        {
            "jobs": 
            [   
                {"jobName": "SND_Frontend_DEV_To_Freeze", "nodeName": "SND_Jenkins"}, 
                {"jobName": "SND_Frontend_Build_App", "nodeName": "master"},
                {"jobName": "SND_Frontend_Deliver", "nodeName": "SND_Jenkins"},
                {"jobName": "SND_Frontend_Downgrade", "nodeName": "SND_Jenkins"},

                {"jobName": "SND_Frontend_Build_Web", "nodeName": "SND_Jenkins"},
                {"jobName": "SND_Frontend_E2E_Test", "nodeName": "SND_Jenkins", "testEnvironment": "實體機"},
                {"jobName": "SND_Frontend_E2E_Test", "nodeName": "E2E_Docker", "testEnvironment": "Docker"}
            ]
        }
    """;
    def parser = new groovy.json.JsonSlurper()
    def json = parser.parseText(configString);
    def resultNode = '';

    json.jobs.each {
        if(it.jobName == executeJobName) {
            if(!!it.testEnvironment && !!testEnvironment) {
                if(it.testEnvironment == testEnvironment) {
                    resultNode = it.nodeName;
                }
            }
            else {
                resultNode = it.nodeName;
            }
        }
    }

    if(resultNode == '') {
        resultNode = 'master';
    }

    each "lib return resultNode: ${resultNode}";
    resultNode;

}