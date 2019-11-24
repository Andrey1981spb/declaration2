package ru.spb.andrey.service;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.spb.andrey.TimingRules;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.springframework.core.NestedExceptionUtils.getRootCause;

@ContextConfiguration ( {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
} )
@RunWith ( SpringRunner.class )
@Sql ( scripts = "classpath:db/populateDB.sql", config = @SqlConfig ( encoding = "UTF-8" ) )
abstract public class AbstractServiceTest {
    @ClassRule
    public static ExternalResource summary = TimingRules.SUMMARY;

    @Rule
    public Stopwatch stopwatch = TimingRules.STOPWATCH;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //  Check root cause in JUnit: https://github.com/junit-team/junit4/pull/778
    public <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        try {
            runnable.run();
            Assert.fail("Expected " + exceptionClass.getName());
        } catch (Exception e) {
            Assert.assertThat(getRootCause(e), instanceOf(exceptionClass));
        }
    }
}