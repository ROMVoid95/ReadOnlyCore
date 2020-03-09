package net.rom.libs.space.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import micdoodle8.mods.galacticraft.api.galaxies.Moon;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, FIELD })
public @interface MoonType {

}
