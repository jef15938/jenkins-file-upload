@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7.1')
@Grab('org.apache.httpcomponents:httpmime:4.2.1')

import org.apache.http.entity.mime.content.* 
import org.apache.http.entity.mime.*
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.POST

def fileUpload(String url, File file, String targetPath){
  println "doPost: $url body: ${file.name}"
  def result 
  try {
    new HTTPBuilder(url).request(POST) { req ->
      requestContentType = "multipart/form-data"

      def content = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE)
      content.addPart("file", new InputStreamBody(file.newInputStream(), file.name))
      content.addPart("targetPath",targetPath)
      req.entity = content

      // json might be something else (like a reader) 
      // depending on the response content type
      response.success = { resp, json -> 
        result = json
        println "RESP: ${resp.statusLine}, RESULT: $json"
      }

      response.failure = { resp, json ->
        println "My response handler got response: ${resp.statusLine}"
      }
    }
  } catch (e) {
    println "Could not perform POST request on URL $url"
    throw e
  }

  result
}