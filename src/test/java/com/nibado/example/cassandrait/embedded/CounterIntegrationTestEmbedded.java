package com.nibado.example.cassandrait.embedded;

import com.nibado.example.cassandrait.ExampleApplication;
import com.nibado.example.cassandrait.base.CounterIntegrationTest;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {ExampleApplication.class, TestConfigurationEmbedded.class})
@EnableConfigurationProperties
public class CounterIntegrationTestEmbedded extends CounterIntegrationTest {

}
