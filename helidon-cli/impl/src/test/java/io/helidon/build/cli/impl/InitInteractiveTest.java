/*
 * Copyright (c) 2020 Oracle and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.helidon.build.cli.impl;

import java.io.File;
import java.nio.file.Path;

import io.helidon.build.cli.impl.InitCommand.Flavor;
import io.helidon.build.test.TestFiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.helidon.build.cli.impl.InitCommand.DEFAULT_NAME;
import static io.helidon.build.cli.impl.InitCommand.DEFAULT_PACKAGE;
import static io.helidon.build.cli.impl.InitCommand.DEFAULT_APPTYPE;
import static io.helidon.build.cli.impl.TestUtils.assertPackageExist;
import static io.helidon.build.cli.impl.TestUtils.execWithDirAndInput;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class InitInteractiveTest.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InitInteractiveTest extends BaseCommandTest {

    private final Path targetDir = TestFiles.targetDir();

    @BeforeEach
    public void precondition() {
        assumeTrue(TestUtils.apptypeArchetypeFound(Flavor.SE, HELIDON_SNAPSHOT_VERSION, DEFAULT_APPTYPE));
    }

    @Test
    @Order(1)
    public void testInitSe() throws Exception {
        File input = new File(InitCommand.class.getResource("input.txt").getFile());
        TestUtils.ExecResult res = execWithDirAndInput(targetDir.toFile(), input,
                "init", "--version ", HELIDON_SNAPSHOT_VERSION);
        System.out.println(res.output);
        assertThat(res.code, is(equalTo(0)));
        assertPackageExist(targetDir.resolve(DEFAULT_NAME), DEFAULT_PACKAGE);
    }

    @Test
    @Order(2)
    public void testCleanSe() {
        Path projectDir = targetDir.resolve(DEFAULT_NAME);
        assertTrue(TestFiles.deleteDirectory(projectDir.toFile()));
        System.out.println("Directory " + projectDir + " deleted");
    }
}