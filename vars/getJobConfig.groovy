@NonCPS
def call(){
//   println "doPost: $url body: ${file.name}"
    println 'in config';
    def builder = new groovy.json.JsonBuilder()
    def configJson = builder.config {
        jobs: [
        {
            jobName: 'SND_Frontend_Build_App',
            nodeName: 'master'
        },
        {
            jobName: 'SND_Frontend_Deliver',
            nodeName: 'SND_Jenkins'
        }]
    };
    
    println configJson;
   
    configJson;
}