package com.unknown.supervisor;

import com.tngtech.archunit.core.importer.ImportOption;
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
@AnalyzeClasses(packages = "com.unknown.supervisor", importOptions = {ImportOption.DoNotIncludeTests.class})
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
                    .should().haveSimpleNameEndingWith("Controller")
                    .andShould().beAnnotatedWith(Tag.class)
                    .andShould().beAnnotatedWith(RestController.class)
                    .andShould().beAnnotatedWith(RequestMapping.class);

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
                    .that().resideInAPackage("com.unknown.supervisor.portal..")
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
                            "org.apache.commons.lang3..",
                            "com.unknown.supervisor.common..",
                            "com.unknown.supervisor.core..",
                            "com.unknown.supervisor.utils..",
                            "com.unknown.supervisor.portal.."
                    );

    @ArchTest
    static final ArchRule controllerOnlyUseVORule =
            noClasses()
                    .that().resideInAPackage("..controller..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..dto..");

    @ArchTest
    static final ArchRule serviceOnlyUseDTORule =
            noClasses()
                    .that().resideInAPackage("..service..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..vo..");

    @ArchTest
    static final ArchRule mapperOnlyUseDTORule =
            noClasses()
                    .that().resideInAPackage("..mapper..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..vo..");
}