grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.ico.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
        //mavenRepo "http://central.maven.org/maven2"
        mavenRepo "http://repo.grails.org/grails/core"
        mavenRepo "https://oss.sonatype.org/content/groups/public/" //wslite repository
    }

    def gebVersion = "0.9.3" // "0.9.3" (Actual working version) // 0.13.1 (latest version)
    def seleniumVersion = "2.41.0"

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        runtime 'mysql:mysql-connector-java:5.1.38'
        // runtime 'org.postgresql:postgresql:9.3-1100-jdbc41'

        //Adds Geb/Spock integration
        test "org.gebish:geb-spock:$gebVersion"

        //Uses Firefox for functional tests
        test "org.seleniumhq.selenium:selenium-support:$seleniumVersion"
        test "org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion"

        //compile "com.google.code.simple-spring-memcached:spymemcached:2.7.3"
        //compile "spy:spymemcached:2.7.3"
    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.55.3" // or ":tomcat:9.0.0.M1" (for Tomcat 9)

        //Geb
        test ":geb:$gebVersion" //Adds Gebs Grails Plugin

        // plugins for the compile step
        compile ":scaffolding:2.0.3"
        compile ":searchable:0.6.9"
        compile ':cache:1.1.8'//, ':cache-ehcache:1.0.5'
        compile ":mysql-connectorj:5.1.22.1"
        compile ":wslite:0.7.2.0"


        // plugins needed at runtime but not for compilation
        runtime ":hibernate:3.6.10.9" // or ":hibernate4:4.3.4"
        runtime ":database-migration:1.3.8"
        runtime ":jquery:1.11.1"
        runtime ":resources:1.2.14"
        runtime ":twitter-bootstrap:3.3.5"
        runtime ":fields:1.5.1"
        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0.1"
        //runtime ":cached-resources:1.1"
        //runtime ":yui-minify-resources:0.1.5"

        // An alternative to the default resources plugin is the asset-pipeline plugin
        //compile ":asset-pipeline:1.6.1"
        compile ":asset-pipeline:2.7.2"

        compile ":jquery-ui:1.10.4"

        compile ":grails-melody:1.59.0"
        //compile ":perf4j:0.1.1"

        compile ":codenarc:0.25.2"

        //compile ":memcached:1.0.3.2"
        runtime ":redis:1.6.6"
        //compile ":redis-gorm:1.0.0"

        // Uncomment these to enable additional asset-pipeline capabilities
        //compile ":sass-asset-pipeline:1.5.5"
        //compile ":less-asset-pipeline:1.5.3"
        //compile ":coffee-asset-pipeline:1.5.0"
        //compile ":handlebars-asset-pipeline:1.3.0.1"
    }
}

//CodeNarc config

codenarc {
    processTestUnit = false
    processTestIntegration = false
    excludeBaseline = ''
    propertiesFile = 'grails-app/conf/codenarc.properties'
    ruleSetFiles = "rulesets/basic.xml,rulesets/exceptions.xml, rulesets/imports.xml,rulesets/grails.xml, rulesets/unused.xml, rulesets/size.xml, rulesets/concurrency.xml,rulesets/convention.xml,rulesets/design.xml,rulesets/groovyism.xml,rulesets/imports.xml,rulesets/logging.xml,rulesets/unnecessary.xml,rulesets/naming.xml,rulesets/braces.xml"
    reports = {
        MyHtmlReport('html') {
            outputFile = 'target/CodeNarc-Report.html'
            title = 'Reporte Codenarc'
        }
        MyHtmlSortableReport('sortable') {
            outputFile = 'target/CodeNarc-Sortable-Report.html'
            title = 'Reporte Codenarc Ordenable'
        }
    }
}