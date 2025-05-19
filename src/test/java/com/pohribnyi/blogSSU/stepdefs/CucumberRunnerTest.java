package com.pohribnyi.blogSSU.stepdefs;

import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber") 
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.pohribnyi.blogSSU.stepdefs")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "not @Ignore")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/blog-post-pretty, json:target/cucumber-reports/json-reports/blog-post.json, junit:target/cucumber-reports/blog-post.xml, rerun:target/cucumber-reports/rerun-reports/rerun.txt")
@ConfigurationParameter(key = "cucumber.execution.monochrome", value = "true")
class CucumberRunnerTest {

}
