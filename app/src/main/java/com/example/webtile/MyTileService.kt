package com.example.webtile

import android.util.Log
import androidx.wear.protolayout.ColorBuilders
import androidx.wear.protolayout.DeviceParametersBuilders
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.material.Button
import androidx.wear.protolayout.material.ButtonColors
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.TileService
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture

class MyTileService : TileService() {

    override fun onTileRequest(requestParams: RequestBuilders.TileRequest): ListenableFuture<TileBuilders.Tile> {
        val tileLayout = tileLayout(requestParams.deviceConfiguration)

        val timeline = TimelineBuilders.Timeline.Builder()
            .addTimelineEntry(
                TimelineBuilders.TimelineEntry.Builder()
                    .setLayout(
                        LayoutElementBuilders.Layout.Builder()
                            .setRoot(tileLayout)
                            .build()
                    )
                    .build()
            )
            .build()

        val tile = TileBuilders.Tile.Builder()
            .setResourcesVersion("1")
            .setTimeline(timeline)
            .build()

        return Futures.immediateFuture(tile)
    }

    override fun onResourcesRequest(requestParams: RequestBuilders.ResourcesRequest): ListenableFuture<ResourceBuilders.Resources> {
        val resources = ResourceBuilders.Resources.Builder()
            .setVersion("1")
            .build()
        return Futures.immediateFuture(resources)
    }

    override fun onTileAddEvent(requestParams: RequestBuilders.TileAddEvent) {
        super.onTileAddEvent(requestParams)
        Log.d(TAG, "Tile added")
    }

    override fun onTileEnterEvent(requestParams: RequestBuilders.TileEnterEvent) {
        super.onTileEnterEvent(requestParams)
        Log.d(TAG, "Tile entered")
    }

    override fun onTileLeaveEvent(requestParams: RequestBuilders.TileLeaveEvent) {
        super.onTileLeaveEvent(requestParams)
        Log.d(TAG, "Tile left")
    }

    override fun onTileClickEvent(requestParams: RequestBuilders.TileClickEvent): ListenableFuture<Void> {
        val clickId = requestParams.lastClickableId
        when (clickId) {
            CLICK_ID_START -> Log.d(TAG, "Tile click: start")
            CLICK_ID_STOP -> Log.d(TAG, "Tile click: stop")
            CLICK_ID_RESET -> Log.d(TAG, "Tile click: reset")
            else -> Log.d(TAG, "Tile click: unknown id=$clickId")
        }
        return Futures.immediateFuture(null)
    }

    private fun tileLayout(deviceParameters: DeviceParametersBuilders.DeviceParameters): LayoutElementBuilders.LayoutElement {
        return LayoutElementBuilders.Column.Builder()
            .setWidth(DimensionBuilders.expand())
            .setHeight(DimensionBuilders.expand())
            .setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER)
            .addContent(
                Text.Builder(this, "WebTile")
                    .setTypography(Typography.TYPOGRAPHY_TITLE3)
                    .setColor(ColorBuilders.argb(0xFFFFFFFF.toInt()))
                    .build()
            )
            .addContent(
                LayoutElementBuilders.Row.Builder()
                    .setWidth(DimensionBuilders.expand())
                    .setHeight(DimensionBuilders.wrap())
                    .setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER)
                    .addContent(tileButton(deviceParameters, "Start", CLICK_ID_START))
                    .addContent(tileButton(deviceParameters, "Stop", CLICK_ID_STOP))
                    .addContent(tileButton(deviceParameters, "Reset", CLICK_ID_RESET))
                    .build()
            )
            .build()
    }

    private fun tileButton(
        deviceParameters: DeviceParametersBuilders.DeviceParameters,
        label: String,
        clickableId: String
    ): LayoutElementBuilders.LayoutElement {
        return Button.Builder(this, ModifiersBuilders.Clickable.Builder().setId(clickableId).build(), deviceParameters)
            .setButtonColors(ButtonColors.primaryButtonColors(0xFF1E88E5.toInt()))
            .setContentDescription(label)
            .setCustomContent(
                Text.Builder(this, label)
                    .setTypography(Typography.TYPOGRAPHY_BUTTON)
                    .setColor(ColorBuilders.argb(0xFFFFFFFF.toInt()))
                    .build()
            )
            .build()
    }

    companion object {
        private const val TAG = "WebTile"
        private const val CLICK_ID_START = "start"
        private const val CLICK_ID_STOP = "stop"
        private const val CLICK_ID_RESET = "reset"
    }
}
