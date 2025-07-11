package com.unknown.supervisor;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "com.unknown.supervisor")
public class ArchUnitTest {
    @ArchTest
    static final ArchRule cycleRule =
            slices()
                    .matching("com.unknown.supervisor.(*)..")
                    .should().beFreeOfCycles();

    @ArchTest
    static final ArchRule controllerRule =
            classes()
                    .that().resideInAPackage("..controller..")
                    .and().areAnnotatedWith(Tag.class)
                    .and().areAnnotatedWith(RestController.class)
                    .and().areAnnotatedWith(RequestMapping.class)
                    .should().haveSimpleNameEndingWith("Controller");

    @ArchTest
    static final ArchRule webLayerRule =
            layeredArchitecture()
                    .consideringAllDependencies()
                    .layer("Controller").definedBy("..controller..")
                    .layer("Service").definedBy("..service..")
                    .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                    .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller");

    @ArchTest
    static final ArchRule frameworkPackageDependRule =
            noClasses()
                    .that().resideInAPackage("com.unknown.supervisor.core..")
                    .should().dependOnClassesThat()
                    .resideOutsideOfPackages(
                            "java..",
                            "org.springframework..",
                            "io.swagger.v3.oas.annotations..",
                            "lombok..",
                            "org.slf4j..",
                            "jakarta..",
                            "org.apache.commons.lang3..",
                            "com.unknown.supervisor.common..",
                            "com.unknown.supervisor.core..",
                            "com.unknown.supervisor.util.."
                    );

    @ArchTest
    static final ArchRule utilsPackageDependRule =
            noClasses()
                    .that().resideInAPackage("com.unknown.supervisor.utils..")
                    .should().dependOnClassesThat()
                    .resideOutsideOfPackages(
                            "java..",
                            "org.springframework..",
                            "io.swagger.v3.oas.annotations..",
                            "lombok..",
                            "org.slf4j..",
                            "jakarta..",
                            "org.apache.commons.lang3..",
                            "com.unknown.supervisor.common.."
                    );

    @ArchTest
    static final ArchRule portalPackageDependRule =
            noClasses()
                    .that().resideInAPackage("com.unknown.supervisor.web.portal..")
                    .should().dependOnClassesThat()
                    .resideOutsideOfPackages(
                            "java..",
                            "org.springframework..",
                            "io.swagger.v3.oas.annotations..",
                            "lombok..",
                            "org.slf4j..",
                            "jakarta..",
                            "com.baomidou.mybatisplus..",
                            "org.apache.ibatis..",
                            "com.unknown.supervisor.common..",
                            "com.unknown.supervisor.core.exception..",
                            "com.unknown.supervisor.web.portal.."
                    );
}