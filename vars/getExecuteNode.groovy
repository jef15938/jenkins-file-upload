import groovy.json.JsonOutput;

@NonCPS
def call(String executeJobName, Object parameter = []){
    echo "!!parameter: ${!!parameter}";
    echo "!!parameter.testEnvironment: ${!!parameter.testEnvironment}" ;
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
                {"jobName": "SND_Frontend_E2E_Test", "nodeName": "SND_Jenkins"}
            ]
        }
    """;
    def parser = new groovy.json.JsonSlurper()
    def json = parser.parseText(configString);
    def resultNode = '';

    json.jobs.each {
        if(it.jobName == executeJobName) {
            resultNode = it.nodeName;
        }
    }

    if(resultNode == '') {
        resultNode = 'master';
    }

    resultNode;

}