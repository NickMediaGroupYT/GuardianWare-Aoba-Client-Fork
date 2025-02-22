/*
 * Aoba Hacked Client
 * Copyright (C) 2019-2024 coltonk9043
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * A class to represent Aoba Client and all of its functions.
 */
package net.aoba;

import com.mojang.logging.LogUtils;
import net.aoba.api.IAddon;
import net.aoba.command.GlobalChat;
import net.aoba.gui.GuiManager;
import net.aoba.gui.font.FontManager;
import net.aoba.managers.*;
import net.aoba.managers.altmanager.AltManager;
import net.aoba.managers.macros.MacroManager;
import net.aoba.managers.proxymanager.ProxyManager;
import net.aoba.managers.rotation.RotationManager;
import net.aoba.mixin.interfaces.IMinecraftClient;
import net.aoba.module.Module;
import net.aoba.settings.friends.FriendsList;
import net.aoba.utils.discord.RPCManager;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AobaClient
{
    public static final String AOBA_VERSION = "Public Build v0.1";

    public static MinecraftClient MC;
    public static IMinecraftClient IMC;

    // Systems
    public RotationManager rotationManager;
    public ModuleManager moduleManager;
    public CommandManager commandManager;
    public AltManager altManager;
    public ProxyManager proxyManager;
    public GuiManager guiManager;
    public FontManager fontManager;
    public CombatManager combatManager;
    public SettingManager settingManager;
    public FriendsList friendsList;
    public GlobalChat globalChat;
    public EventManager eventManager;
    public MacroManager macroManager;
    public EntityManager entityManager;

    public static List<IAddon> addons = new ArrayList<>();
    private static Logger LOGGER;


    /**
     * Initializes Aoba Client and creates sub-systems.
     */
    public void Initialize()
    {
        // Gets instance of Minecraft
        MC = MinecraftClient.getInstance();
        IMC = (IMinecraftClient) MC;
        LOGGER = LogUtils.getLogger();
    }

    /**
     * Initializes systems and loads any assets.
     */
    public void loadAssets()
    {
        LOGGER.info("[GuardianWare] Starting Client");
        eventManager = new EventManager();

        // Register any addons.
        LogUtils.getLogger().info("[GuardianWare] Starting addon initialization");
        for (EntrypointContainer<IAddon> entrypoint : FabricLoader.getInstance().getEntrypointContainers("aoba", IAddon.class))
        {
            IAddon addon = entrypoint.getEntrypoint();

            try
            {
                LOGGER.info("[GuardianWare] Initializing addon: " + addon.getName());
                addon.onInitialize();
                LOGGER.info("[GuardianWare] Addon initialized: " + addon.getName());
            } catch (Throwable e)
            {
                LOGGER.error("Error initializing addon: " + addon.getName(), e.getMessage());
            }

            addons.add(addon);
        }

        LOGGER.info("[GuardianWare] Reading Settings");
        settingManager = new SettingManager();

        LOGGER.info("[GuardianWare] Reading Friends List");
        friendsList = new FriendsList();

        LOGGER.info("[GuardianWare] Initializing Rotation Manager");
        rotationManager = new RotationManager();

        LOGGER.info("[GuardianWare] Initializing Modules");
        moduleManager = new ModuleManager(addons);

        LOGGER.info("[GuardianWare] Initializing Commands");
        commandManager = new CommandManager(addons);

        LOGGER.info("[GuardianWare] Initializing Font Manager");
        fontManager = new FontManager();
        fontManager.Initialize();

        LOGGER.info("[GuardianWare] Initializing Combat Manager");
        combatManager = new CombatManager();

        LOGGER.info("[GuardianWare] Initializing Entity Manager");
        entityManager = new EntityManager();

        LOGGER.info("[GuardianWare] Initializing Macro Manager");
        macroManager = new MacroManager();

        LOGGER.info("[GuardianWare] Initializing GUI");
        guiManager = new GuiManager();
        guiManager.Initialize();

        LOGGER.info("[GuardianWare] Initializing Alt Manager");
        altManager = new AltManager();

        LOGGER.info("[GuardianWare] Initializing Proxy Manager");
        proxyManager = new ProxyManager();

        LOGGER.info("[GuardianWare] Registering Shutdown Hook");
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            try
            {
                endClient();
            } catch (Exception e)
            {
                LOGGER.error("[Aoba] Error during shutdown: ", e);
            }
        }));

        LOGGER.info("[GuardianWare] Loading Settings");
        SettingManager.loadGlobalSettings();
        SettingManager.loadSettings();

        LOGGER.info("[GuardianWare] Initializing Global Chat");
        globalChat = new GlobalChat();
        globalChat.StartListener();

        LOGGER.info("[GuardianWare] Guardian-chan initialized and ready to play!");

        // GuiManager.borderColor.setMode(ColorMode.Rainbow);
        // GuiManager.foregroundColor.setMode(ColorMode.Random);
    }

    /**
     * Called when the client is shutting down. Saves persistent data.
     */
    public void endClient() {
        LOGGER.info("[GuardianWare] Shutting down");
        try {
            SettingManager.saveSettings();
            altManager.saveAlts();
            friendsList.save();
            macroManager.save();
            moduleManager.modules.forEach(Module::onDisable);
        } catch (Exception e) {
            LOGGER.error("[GuardianWare] Error saving data", e);
        }
    }
}
