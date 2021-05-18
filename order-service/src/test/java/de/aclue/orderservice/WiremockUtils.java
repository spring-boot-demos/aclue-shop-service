package de.aclue.orderservice;

import java.util.Arrays;
import java.util.List;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.core.WireMockApp;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import com.github.tomakehurst.wiremock.standalone.MappingsLoader;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.github.tomakehurst.wiremock.stubbing.StubMappings;

/**
 * Utils class for initialization of wire mock based mocks.
 * <p>
 * Usage in Tests <code> &#64;TestConfiguration static class Config {
 * <p>
 * &#64;Bean(destroyMethod = "shutdown") public WireMockServer exampleWireMockServer() { return WireMockUtils.initMockServer("example",
 * "http://localhost:20200", 60200, 0, true); } }
 * </code>
 */
public final class WiremockUtils {

  private static List<String> requestHeaderForMatching = Arrays.asList("Content-Type", "Accept");

  private WiremockUtils() {
  }


  public static WireMockServer initMockServer(String recordFolder, String proxiedSystem, int mockPort, int globalFixedDelay,
      boolean produceNewRecords) {
    WireMockConfiguration wireMockConfiguration = new WireMockConfiguration();
    wireMockConfiguration.withRootDirectory("src/test/resources/wiremock/" + recordFolder)
        .port(mockPort)
        .httpsPort(mockPort + 1)
        .recordRequestHeadersForMatching(requestHeaderForMatching)
        .notifier(new Slf4jNotifier(true));

    WireMockServer wireMockServer = new WireMockServer(wireMockConfiguration);
    wireMockServer.setGlobalFixedDelay(globalFixedDelay);

    if (produceNewRecords) {
      wireMockServer.loadMappingsUsing(new MappingsLoader() {
        @Override
        public void loadMappingsInto(StubMappings stubMappings) {
          RequestPattern requestPattern = RequestPatternBuilder.newRequestPattern(RequestMethod.ANY, WireMock.anyUrl()).build();
          ResponseDefinition responseDef = ResponseDefinitionBuilder.responseDefinition().proxiedFrom(proxiedSystem).build();

          StubMapping proxyBasedMapping = new StubMapping(requestPattern, responseDef);
          proxyBasedMapping.setPriority(10); // Make it low priority so that existing stubs will take precedence
          stubMappings.addMapping(proxyBasedMapping);
        }
      });
    }

    FileSource fileSource = wireMockConfiguration.filesRoot();
    fileSource.createIfNecessary();
    FileSource filesFileSource = fileSource.child(WireMockApp.FILES_ROOT);
    filesFileSource.createIfNecessary();
    FileSource mappingsFileSource = fileSource.child(WireMockApp.MAPPINGS_ROOT);
    mappingsFileSource.createIfNecessary();

    wireMockServer.enableRecordMappings(mappingsFileSource, filesFileSource);

    wireMockServer.start();
    return wireMockServer;
  }
}