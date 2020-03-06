package net.rom.libs.utils;

import javax.annotation.Nonnull;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class TextUtil extends TextComponentString {
    public TextUtil() {
        super("");
    }

    public TextUtil(TextFormatting color) {
        super("");
        getStyle().setColor(color);
    }

    @Nonnull
    @Override
    public String getUnformattedComponentText() {
        return "";
    }

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

    public TextUtil string(String s) {
        this.appendSibling(new TextComponentString(s));
        return this;
    }

    public TextUtil string(String s, TextFormatting color) {
        ITextComponent t = new TextComponentString(s);
        t.getStyle().setColor(color);
        this.appendSibling(t);
        return this;
    }

    public TextUtil translation(String key) {
        this.appendSibling(new TextComponentTranslation(key));
        return this;
    }

    public TextUtil translation(String key, Object... args) {
        this.appendSibling(new TextComponentTranslation(key, args));
        return this;
    }

    public TextUtil translation(String key, TextFormatting color) {
        ITextComponent t = new TextComponentTranslation(key);
        t.getStyle().setColor(color);
        this.appendSibling(t);
        return this;
    }

    public TextUtil translation(String key, TextFormatting color, Object... args) {
        ITextComponent t = new TextComponentTranslation(key, args);
        t.getStyle().setColor(color);
        this.appendSibling(t);
        return this;
    }

    public TextUtil component(ITextComponent component) {
        this.appendSibling(component);
        return this;
    }

    public TextUtil component(ITextComponent component, TextFormatting color) {
        component.getStyle().setColor(color);
        this.appendSibling(component);
        return this;
    }
}
