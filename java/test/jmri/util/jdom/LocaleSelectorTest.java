package jmri.util.jdom;

import jmri.util.JUnitUtil;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.junit.Assert;
import org.junit.jupiter.api.*;

/**
 * Tests for the jmri.util.LocaleSelector class.
 *
 * @author Bob Jacobsen Copyright 2010
 */
public class LocaleSelectorTest {

    @Test
    public void testFindDefault() {
        LocaleSelector.setSuffixes( new String[]{"kl_KL", "kl" });

        Namespace xml = Namespace.XML_NAMESPACE;
        Element el = new Element("foo")
                .setAttribute("temp", "a")
                .addContent(
                        new Element("temp")
                        .setAttribute("lang", "hh", xml)
                        .addContent("b")
                );

        String result = LocaleSelector.getAttribute(el, "temp");
        Assert.assertEquals("find default", "a", result);
    }

    @Test
    public void testFindFullCode() {
        LocaleSelector.setSuffixes( new String[]{"kl_KL", "kl"});

        Namespace xml = Namespace.XML_NAMESPACE;
        Element el = new Element("foo")
                .setAttribute("temp", "a")
                .addContent(
                        new Element("temp")
                        .setAttribute("lang", "aa_BB", xml)
                        .addContent("b")
                )
                .addContent(
                        new Element("temp")
                        .setAttribute("lang", "kl", xml)
                        .addContent("b")
                )
                .addContent(
                        new Element("temp")
                        .setAttribute("lang", "kl_KL", xml)
                        .addContent("c")
                );

        String result = LocaleSelector.getAttribute(el, "temp");
        Assert.assertEquals("find default", "c", result);
    }

    @Test
    public void testFindPartialCode() {
        LocaleSelector.setSuffixes( new String[]{"kl_KL", "kl"});

        Namespace xml = Namespace.XML_NAMESPACE;
        Element el = new Element("foo")
                .setAttribute("temp", "a")
                .addContent(
                        new Element("temp")
                        .setAttribute("lang", "aa_BB", xml)
                        .addContent("b")
                )
                .addContent(
                        new Element("temp")
                        .setAttribute("lang", "kl", xml)
                        .addContent("c")
                )
                .addContent(
                        new Element("temp")
                        .setAttribute("lang", "kl_AA", xml)
                        .addContent("d")
                );

        String result = LocaleSelector.getAttribute(el, "temp");
        Assert.assertEquals("find default", "c", result);
    }

    @Test
    public void testDefaultAttribute() {
        LocaleSelector.setSuffixes( new String[]{"kl_KL", "kl"});

        Element el = new Element("foo")
                .setAttribute("temp", "a");

        String result = LocaleSelector.getAttribute(el, "temp");
        Assert.assertEquals("find default", "a", result);
    }

    @Test
    public void testDefaultElement() {
        LocaleSelector.setSuffixes( new String[]{"kl_KL", "kl"});

        Element el = new Element("foo")
                .addContent(
                        new Element("temp")
                        .addContent("b")
                );

        String result = LocaleSelector.getAttribute(el, "temp");
        Assert.assertEquals("find default", "b", result);
    }

    @Test
    public void testFindFullCodeNoAttribute() {
        LocaleSelector.setSuffixes( new String[]{"kl_KL", "kl"});

        Namespace xml = Namespace.XML_NAMESPACE;
        Element el = new Element("foo")
                .addContent(
                        new Element("temp")
                        .setAttribute("lang", "aa_BB", xml)
                        .addContent("b")
                )
                .addContent(
                        new Element("temp")
                        .setAttribute("lang", "kl", xml)
                        .addContent("b")
                )
                .addContent(
                        new Element("temp")
                        .setAttribute("lang", "kl_KL", xml)
                        .addContent("c")
                );

        String result = LocaleSelector.getAttribute(el, "temp");
        Assert.assertEquals("find default", "c", result);
    }

    @Test
    public void testFindPartialCodeNoAttribute() {
        LocaleSelector.setSuffixes( new String[]{"kl_KL", "kl"});

        Namespace xml = Namespace.XML_NAMESPACE;
        Element el = new Element("foo")
                .addContent(
                        new Element("temp")
                        .setAttribute("lang", "aa_BB", xml)
                        .addContent("b")
                )
                .addContent(
                        new Element("temp")
                        .setAttribute("lang", "kl", xml)
                        .addContent("c")
                )
                .addContent(
                        new Element("temp")
                        .setAttribute("lang", "kl_AA", xml)
                        .addContent("d")
                );

        String result = LocaleSelector.getAttribute(el, "temp");
        Assert.assertEquals("find default", "c", result);
    }

    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
    }

    @AfterEach
    public void tearDown() {
        JUnitUtil.tearDown();
    }
}
