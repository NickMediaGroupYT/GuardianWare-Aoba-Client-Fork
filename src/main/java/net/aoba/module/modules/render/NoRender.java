/*
 * Aoba Hacked Client
 * Copyright (C) 2019-2024 coltonk9043
 *
 * Licensed under the GNU General Public License, Version 3 or later.
 * See <http://www.gnu.org/licenses/>.
 */

package net.aoba.module.modules.render;

import net.aoba.module.Category;
import net.aoba.module.Module;
import net.aoba.settings.types.BooleanSetting;

public class NoRender extends Module {

	private final BooleanSetting noEatParticles = BooleanSetting.builder().id("norender_eat_particles")
			.displayName("Eating Particles").description("Does not render eating particles.").defaultValue(false)
			.build();

	private final BooleanSetting noTotemAnimation = BooleanSetting.builder().id("norender_totem_anim")
			.displayName("Totem Animation").description("Does not render the totem floating animation.")
			.defaultValue(false).build();

	private final BooleanSetting noVignette = BooleanSetting.builder().id("norender_vignette").displayName("Vignette")
			.description("Does not render the Minecraft vignette.").defaultValue(false).build();

	private final BooleanSetting noPumpkinOverlay = BooleanSetting.builder().id("norender_pumpkin")
			.displayName("Pumpkin Overlay").description("Does not render the pumpkin overlay when you are wearing one.")
			.defaultValue(false).build();

	private final BooleanSetting noFireOverlay = BooleanSetting.builder().id("norender_fire").displayName("Fire Overlay")
			.description("Does not render the overlay when the player is on fire.").defaultValue(false).build();

	private final BooleanSetting noPortalOverlay = BooleanSetting.builder().id("norender_portal")
			.displayName("Portal Overlay").description("Does not render the overlay when travelling through a portal.")
			.defaultValue(false).build();

	private final BooleanSetting noPowderSnowOverlay = BooleanSetting.builder().id("norender_powder_snow")
			.displayName("Powder Snow Overlay").description("Does not render the overlay when in powder snow.")
			.defaultValue(false).build();

	private final BooleanSetting noLiquidOverlay = BooleanSetting.builder().id("norender_liquid")
			.displayName("Liquid Overlay").description("Does not render the overlay when in liquids.")
			.defaultValue(false).build();

	private final BooleanSetting noCrosshair = BooleanSetting.builder().id("norender_crosshair").displayName("Crosshair")
			.description("Does not render the crosshair.").defaultValue(false).build();

	public NoRender() {
		super("NoRender");
		this.setCategory(Category.of("Render"));
		this.setDescription("Stops certain things from rendering.");

		this.addSetting(noEatParticles);
		this.addSetting(noTotemAnimation);
		this.addSetting(noVignette);
		this.addSetting(noPumpkinOverlay);
		this.addSetting(noFireOverlay);
		this.addSetting(noPortalOverlay);
		this.addSetting(noPowderSnowOverlay);
		this.addSetting(noLiquidOverlay);
		this.addSetting(noCrosshair);
	}

	@Override
	public void onDisable() {

	}

	@Override
	public void onEnable() {

	}

	@Override
	public void onToggle() {

	}

	public boolean getNoEatParticles() {
		return this.noEatParticles.getValue();
	}

	public boolean getNoTotemAnimation() {
		return this.noTotemAnimation.getValue();
	}

	public boolean getNoVignette() {
		return this.noVignette.getValue();
	}

	public boolean getNoPumpkinOverlay() {
		return this.noPumpkinOverlay.getValue();
	}

	public boolean getNoFireOverlay() {
		return this.noFireOverlay.getValue();
	}

	public boolean getNoPortalOverlay() {
		return this.noPumpkinOverlay.getValue();
	}

	public boolean getNoPowderSnowOverlay() {
		return this.noPowderSnowOverlay.getValue();
	}

	public boolean getNoLiquidOverlay() {
		return this.noLiquidOverlay.getValue();
	}

	public boolean getNoCrosshair() {
		return this.noCrosshair.getValue();
	}
}
