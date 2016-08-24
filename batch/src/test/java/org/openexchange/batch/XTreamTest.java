package org.openexchange.batch;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

public class XTreamTest {
    @Test
    public void testXTreamParsingIssue() throws Exception {
        String xml = "{\"map\":[{\"entry\":[{\"string\":[\"batch.taskletType\",\"org.springframework.batch.core.step.item.ChunkOrientedTasklet\"]},{\"string\":[\"batch.stepType\",\"org.springframework.batch.core.step.tasklet.TaskletStep\"]}]}]}";
        XStream xtream = new XStream(new JettisonMappedXmlDriver());
        Object o = xtream.fromXML(new StringReader(xml));
        Assert.assertNotNull(o);
    }
}
