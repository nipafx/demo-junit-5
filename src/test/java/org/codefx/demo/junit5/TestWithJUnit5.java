package org.codefx.demo.junit5;

import org.junit.platform.runner.IncludeClassNamePattern;
import org.junit.platform.runner.IncludeEngines;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.runner.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages({ "org.codefx.demo.junit5" })
@IncludeClassNamePattern(".*Tests?")
@IncludeEngines("junit-jupiter")
public class TestWithJUnit5 { }
