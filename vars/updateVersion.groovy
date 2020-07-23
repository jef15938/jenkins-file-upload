@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7.1')
@Grab('org.apache.httpcomponents:httpmime:4.2.1')

import org.apache.http.entity.mime.content.* 
import org.apache.http.entity.mime.*
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.POST

@NonCPS
def call(String url, String version, String platform, String downloadLink){
  println "Update Version url: ${url}, version: ${version}, platform: ${platform}, downloadLink: ${downloadLink}"
  def result 
  try {
    new HTTPBuilder(url).request(POST) { req ->
      requestContentType = "multipart/form-data"

      def content = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE)
      content.addPart("version", new StringBody(version, org.apache.http.entity.ContentType.TEXT_PLAIN))
      content.addPart("deviceSystem", new StringBody(platform, org.apache.http.entity.ContentType.TEXT_PLAIN))
      content.addPart("appLink", new StringBody(downloadLink, org.apache.http.entity.ContentType.TEXT_PLAIN))
      content.addPart("isEnabled", new StringBody('Y', org.apache.http.entity.ContentType.TEXT_PLAIN))
      content.addPart("description", new StringBody(version, org.apache.http.entity.ContentType.TEXT_PLAIN))

      req.entity = content

      // json might be something else (like a reader) 
      // depending on the response content type
      response.success = { resp, json -> 
        result = json
        println "RESP: ${resp.statusLine}, RESULT: ${json}"
      }

      response.failure = { resp, json ->
        println "My response handler got response: ${resp.statusLine}"
      }
    }
  } catch (e) {
    println "Could not perform POST request on URL ${url}"
    throw e
  }

  result
}