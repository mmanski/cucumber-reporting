package net.masterthought.cucumber.json;

import mockit.Deencapsulation;
import net.masterthought.cucumber.generators.integrations.PageTest;
import net.masterthought.cucumber.json.support.Status;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class FeatureTest extends PageTest {

    @Before
    public void setUp() {
        setUpWithJson(SAMPLE_JSON);
    }

    @Test
    public void getDeviceName_ReturnsDeviceName() {

        // given
        Feature feature = features.get(0);

        // when
        String deviceName = feature.getDeviceName();

        // when
        assertThat(deviceName).isEqualTo("sample");
    }

    @Test
    public void getId_ReturnsID() {

        // given
        Feature feature = features.get(1);

        // when
        String id = feature.getId();

        // then
        assertThat(id).isEqualTo("account-holder-withdraws-more-cash");
    }

    @Test
    public void getElements_ReturnsElements() {

        // given
        Feature feature = features.get(0);

        // when
        Element[] elements = feature.getElements();

        // then
        assertThat(elements).hasSize(2);
        assertThat(elements[0].getEscapedName()).isEqualTo("Activate Credit Card");
    }

    @Test
    public void getReportFileName_ReturnsFileName() {

        // given
        Feature feature = features.get(1);

        // when
        String fileName = feature.getReportFileName();

        // then
        assertThat(fileName).isEqualTo("net-masterthought-example-ATMK-feature.html");
    }

    @Test
    public void getReportFileName_OnParallelTesting_ReturnsFileNameWithNumberPostfix() {

        // given
        configuration.setParallelTesting(true);
        setUpWithJson(SAMPLE_JSON);
        Feature feature = features.get(2);

        // when
        String fileName = feature.getReportFileName();

        // then
        assertThat(fileName).isEqualTo("net-masterthought-example-ATMK-feature_sample.html");
    }

    @Test
    public void getTags_ReturnsTags() {

        // given
        Element element = features.get(1).getElements()[0];

        // when
        Tag[] tags = element.getTags();

        // then
        assertThat(tags).hasSize(1);
        assertThat(tags[0].getName()).isEqualTo("@checkout");
    }

    @Test
    public void getStatus_ReturnsStatus() {

        // given
        Feature feature = features.get(1);

        // when
        Status status = feature.getStatus();

        // then
        assertThat(status).isEqualTo(Status.FAILED);
    }

    @Test
    public void getName_ReturnsName() {

        // given
        Feature feature = features.get(0);

        // when
        String name = feature.getName();

        // then
        assertThat(name).isEqualTo("1st feature");
    }

    @Test
    public void getKeyword_ReturnsKeyword() {

        // given
        Feature feature = features.get(0);

        // when
        String keyword = feature.getKeyword();

        // then
        assertThat(keyword).isEqualTo("Feature");
    }

    @Test
    public void getDescription_ReturnsDescription() {

        // given
        Feature feature = features.get(0);

        // when
        String description = feature.getDescription();

        // then
        assertThat(description).isEqualTo("This is description of the feature");
    }

    @Test
    public void getScenarios_ReturnsNumberOfScenarios() {

        // given
        Feature feature = features.get(0);

        // when
        int scenarioCounter = feature.getScenarios();

        // then
        assertThat(scenarioCounter).isEqualTo(1);
    }

    @Test
    public void getSteps_ReturnsNumberOfSteps() {

        // given
        Feature feature = features.get(0);

        // when
        int stepsCounter = feature.getSteps();

        // then
        assertThat(stepsCounter).isEqualTo(11);
    }

    @Test
    public void getXXXSteps_ReturnsStepsForStatus() {

        // given
        Feature feature1 = features.get(0);
        Feature feature2 = features.get(1);

        // then
        assertThat(feature1.getPassedSteps()).isEqualTo(8);
        assertThat(feature2.getFailedSteps()).isEqualTo(0);
        assertThat(feature1.getPendingSteps()).isEqualTo(2);
        assertThat(feature2.getSkippedSteps()).isEqualTo(4);
        assertThat(feature1.getUndefinedSteps()).isEqualTo(1);
    }

    @Test
    public void getDuration_ReturnsDuration() {

        // given
        Feature feature = features.get(0);

        // when
        long duration = feature.getDurations();

        // then
        assertThat(duration).isEqualTo(99353122889L);
    }

    @Test
    public void getFormattedDurations_ReturnsFormattedDurations() {

        // given
        Feature feature = features.get(1);

        // when
        String formattedDuration = feature.getFormattedDurations();

        // then
        assertThat(formattedDuration).isEqualTo("002ms");
    }

    @Test
    public void getXXXScenarios_ReturnsScenariosForStatus() {

        // given
        Feature feature = features.get(1);

        // then
        assertThat(feature.getPassedScenarios()).isEqualTo(1);
        assertThat(feature.getFailedScenarios()).isEqualTo(1);
    }

    @Test
    public void getJsonFile_ReturnsFileName() {

        // given
        Feature feature = features.get(0);

        // when
        String fileName = feature.getJsonFile();

        // then
        assertThat(fileName).endsWith(SAMPLE_JSON);
    }

    @Test
    public void compareTo_OnSameFeature_ReturnsZero() {

        // given
        Feature feature1 = features.get(0);
        Feature feature2 = features.get(0);

        // when
        int result = feature1.compareTo(feature2);

        // then
        assertThat(result).isZero();
    }

    @Test
    public void compareTo_OnSameName_ReturnsNotZero() {

        // given
        Feature feature1 = features.get(0);
        Feature feature2 = buildFeature(feature1.getName(), "myId", "myFile.json");

        // then
        int result = feature1.compareTo(feature2);

        // then
        assertThat(result).isEqualTo(feature1.getId().compareTo(feature2.getId()));
    }

    @Test
    public void compareTo_OnSameIDAndName_ReturnsNotZero() {

        // given
        Feature feature1 = features.get(0);
        Feature feature2 = buildFeature(feature1.getName(), feature1.getId(), "myFile.json");

        // then
        int result = feature1.compareTo(feature2);

        // then
        assertThat(result).isEqualTo(feature1.getJsonFile().compareTo(feature2.getJsonFile()));
    }

    private static Feature buildFeature(final String name, final String id, final String jsonFile) {
        Feature feature = new Feature();
        Deencapsulation.setField(feature, "name", name);
        Deencapsulation.setField(feature, "id", id);
        Deencapsulation.setField(feature, "jsonFile", jsonFile);

        return feature;
    }
}