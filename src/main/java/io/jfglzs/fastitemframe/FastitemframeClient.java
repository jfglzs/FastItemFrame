package io.jfglzs.fastitemframe;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastitemframeClient implements ClientModInitializer {
    private static Logger LOGGER = LoggerFactory.getLogger("Fastitemframe");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Loading FIF Issues Report: https://github.com/jfglzs/FastItemFrame/issues");
    }
}
