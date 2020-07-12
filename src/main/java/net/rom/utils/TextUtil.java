package net.rom.utils;

import javax.annotation.Nonnull;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;


/**
 * The Class TextUtil.
 */
public class TextUtil extends TextComponentString {
    
    /**
     * Instantiates a new text util.
     */
    public TextUtil() {
        super("");
    }

    /**
     * Instantiates a new text util.
     *
     * @param color the color
     */
    public TextUtil(TextFormatting color) {
        super("");
        getStyle().setColor(color);
    }

    /**
     * Gets the unformatted component text.
     *
     * @return the unformatted component text
     */
    @Nonnull
    @Override
    public String getUnformattedComponentText() {
        return "";
    }

    /**
     * Creates the copy.
     *
     * @return the text util
     */
    @Nonnull
    @Override
    public TextUtil createCopy() {
    	TextUtil textcomponentstring = new TextUtil();
        textcomponentstring.setStyle(this.getStyle().createShallowCopy());
        for (ITextComponent itextcomponent : this.getSiblings()) {
            textcomponentstring.appendSibling(itextcomponent.createCopy());
        }
        return textcomponentstring;
    }

    /**
     * String.
     *
     * @param s the s
     * @return the text util
     */
    public TextUtil string(String s) {
        this.appendSibling(new TextComponentString(s));
        return this;
    }

    /**
     * String.
     *
     * @param s the s
     * @param color the color
     * @return the text util
     */
    public TextUtil string(String s, TextFormatting color) {
        ITextComponent t = new TextComponentString(s);
        t.getStyle().setColor(color);
        this.appendSibling(t);
        return this;
    }

    /**
     * Translation.
     *
     * @param key the key
     * @return the text util
     */
    public TextUtil translation(String key) {
        this.appendSibling(new TextComponentTranslation(key));
        return this;
    }

    /**
     * Translation.
     *
     * @param key the key
     * @param args the args
     * @return the text util
     */
    public TextUtil translation(String key, Object... args) {
        this.appendSibling(new TextComponentTranslation(key, args));
        return this;
    }

    /**
     * Translation.
     *
     * @param key the key
     * @param color the color
     * @return the text util
     */
    public TextUtil translation(String key, TextFormatting color) {
        ITextComponent t = new TextComponentTranslation(key);
        t.getStyle().setColor(color);
        this.appendSibling(t);
        return this;
    }

    /**
     * Translation.
     *
     * @param key the key
     * @param color the color
     * @param args the args
     * @return the text util
     */
    public TextUtil translation(String key, TextFormatting color, Object... args) {
        ITextComponent t = new TextComponentTranslation(key, args);
        t.getStyle().setColor(color);
        this.appendSibling(t);
        return this;
    }

    /**
     * Component.
     *
     * @param component the component
     * @return the text util
     */
    public TextUtil component(ITextComponent component) {
        this.appendSibling(component);
        return this;
    }

    /**
     * Component.
     *
     * @param component the component
     * @param color the color
     * @return the text util
     */
    public TextUtil component(ITextComponent component, TextFormatting color) {
        component.getStyle().setColor(color);
        this.appendSibling(component);
        return this;
    }
}
