@NonCPS
def call(){
//   println "doPost: $url body: ${file.name}"
    println 'in config';
    def configJson = {
        jobs: [
            {
                jobName: 'SND_Frontend_Build_App',
                nodeName: 'master'
            },
            {
                jobName: 'SND_Frontend_Deliver',
                nodeName: 'SND_Jenkins'
            }
        ]
    };

    def result = new JsonBuilder(configJson).toPrettyString();
    println result;
   
    result
}