package whirlfrenzy.configurableitemdespawnduration.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import whirlfrenzy.configurableitemdespawnduration.ConfigurableItemDespawnDuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapEditorScreen extends Screen {

    Screen parent;
    TextFieldWidget keyTextField;
    TextFieldWidget valueTextField;
    ButtonWidget addEntryButton;
    ButtonWidget backButton;
    HashMapList list;

    protected HashMapEditorScreen(Text title) {
        super(title);
    }

    public HashMapEditorScreen(Screen parent){
        this(Text.translatable("configurable-item-despawn-duration.config.mapEditorScreenTitle"));
        this.parent = parent;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(context);
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 10, -1);

        // "Remote" is basically just LAN
        if(this.client.isIntegratedServerRunning() && this.client.getServer().isRemote()){
            context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("configurable-item-despawn-duration.config.lanWarning"), this.width / 2, this.height - 10, -1);
        }

        context.drawText(this.textRenderer, Text.translatable("configurable-item-despawn-duration.config.mapEditorKeyFieldLabel"), this.width / 2 - 155, 26, -1, true);
        context.drawText(this.textRenderer, Text.translatable("configurable-item-despawn-duration.config.mapEditorValueFieldLabel"), this.width / 2 + 5, 26, -1, true);

        context.drawText(this.client.textRenderer, "s", this.width / 2 + 42, 41, -1, true);

        this.keyTextField.render(context, mouseX, mouseY, delta);
        this.valueTextField.render(context, mouseX, mouseY, delta);
        this.addEntryButton.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void init(){
        this.keyTextField = new TextFieldWidget(this.textRenderer, this.width / 2 - 155, 35, 150, 20, Text.translatable("configurable-item-despawn-duration.config.mapEditorKeyFieldLabel"));
        this.keyTextField.setTextPredicate(text -> {
            if(text.isEmpty()){
                this.keyTextField.setEditableColor(-1);
                this.keyTextField.setTooltip(null);

                return true;
            }

            Identifier id = Identifier.tryParse(text);
            if(id == null || !Registries.ITEM.containsId(id)) {
                this.keyTextField.setEditableColor(0xFFFF7777);
                this.keyTextField.setTooltip(Tooltip.of(Text.translatable("configurable-item-despawn-duration.config.invalidItem").formatted(Formatting.RED)));
            } else if(ConfigurableItemDespawnDurationConfig.despawnDurations.containsKey(id.toString())){
                this.keyTextField.setEditableColor(0xFFFF7777);
                this.keyTextField.setTooltip(Tooltip.of(Text.translatable("configurable-item-despawn-duration.config.itemAlreadyPresent").formatted(Formatting.RED)));
            } else {
                this.keyTextField.setEditableColor(-1);
                this.keyTextField.setTooltip(null);
            }

            return true;
        });
        this.keyTextField.setPlaceholder(Text.translatable("configurable-item-despawn-duration.config.mapEditorKeyFieldPlaceholder").formatted(Formatting.GRAY));

        this.valueTextField = new TextFieldWidget(this.textRenderer, this.width / 2 + 5, 35, 35, 20, Text.translatable("configurable-item-despawn-duration.config.value"));
        this.valueTextField.setMaxLength(4);
        this.valueTextField.setTextPredicate(text -> {
            if(text.isEmpty()){
                this.valueTextField.setEditableColor(-1);
                this.valueTextField.setTooltip(null);

                return true;
            }

            int seconds;
            try {
                seconds = Integer.parseInt(text);
            } catch(NumberFormatException e){
                this.valueTextField.setEditableColor(0xFFFF7777);
                this.valueTextField.setTooltip(Tooltip.of(Text.translatable("configurable-item-despawn-duration.config.notANumber").formatted(Formatting.RED)));

                return true;
            }

            if(seconds > 1800){
                this.valueTextField.setEditableColor(0xFFFF7777);
                this.valueTextField.setTooltip(Tooltip.of(Text.translatable("configurable-item-despawn-duration.config.maximumValue", 1800).formatted(Formatting.RED)));
            } else if(seconds < 0){
                this.valueTextField.setEditableColor(0xFFFF7777);
                this.valueTextField.setTooltip(Tooltip.of(Text.translatable("configurable-item-despawn-duration.config.minimumValue", 0).formatted(Formatting.RED)));
            } else {
                this.valueTextField.setEditableColor(-1);
                this.valueTextField.setTooltip(null);
            }

            return true;
        });


        this.addEntryButton = new ButtonWidget.Builder(Text.literal("+"), (widget) -> {
            try {
                if(this.keyTextField.getText().isEmpty() || this.valueTextField.getText().isEmpty()){
                    return;
                }

                Identifier id = Identifier.tryParse(this.keyTextField.getText());
                if(id == null || ConfigurableItemDespawnDurationConfig.despawnDurations.containsKey(id.toString()) || !Registries.ITEM.containsId(id)){
                    return;
                }

                int seconds = Integer.parseInt(this.valueTextField.getText());
                if(seconds > ConfigurableItemDespawnDuration.THIRTY_MINUTES_IN_SECONDS || seconds < 0){
                    return;
                }

                ConfigurableItemDespawnDurationConfig.despawnDurations.putFirst(id.toString(), seconds);
                this.list.addToTop(Map.entry(id.toString(), seconds));

                this.keyTextField.setText("");
                this.valueTextField.setText("");
            } catch(NumberFormatException ignored){
            }
        }).dimensions(this.width / 2 + 55, 35, 20, 20).tooltip(Tooltip.of(Text.translatable("configurable-item-despawn-duration.config.add"))).build();

        this.backButton = new ButtonWidget.Builder(Text.translatable("gui.done"), (widget) -> {
            this.close();
        }).dimensions(this.width / 2 - 75, this.height - 30,150, 20).build();

        this.list = new HashMapList(this.client, this.width, this.height - 105, 65, this.height - 40, 25);

        for(HashMap.Entry<String, Integer> entry : ConfigurableItemDespawnDurationConfig.despawnDurations.entrySet()){
            this.list.add(entry);
        }

        this.addDrawableChild(this.keyTextField);
        this.addDrawableChild(this.valueTextField);
        this.addDrawableChild(this.addEntryButton);
        this.addDrawableChild(this.backButton);
        this.addDrawableChild(this.list);
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button){
        if(!this.list.isMouseOver(mouseX, mouseY) && this.list.getFocused() != null){
            this.list.getFocused().valueTextField.setFocused(false);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public static class HashMapList extends ElementListWidget<ListEntry> {
        public HashMapList(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight) {
            super(minecraftClient, width, height, top, bottom, itemHeight);
        }

        public void add(HashMap.Entry<String, Integer> entry){
            this.addEntry(new ListEntry(entry, this.width, this));
        }

        public void addToTop(HashMap.Entry<String, Integer> entry){
            this.addEntryToTop(new ListEntry(entry, this.width, this));
        }

        @Override
        protected void addEntryToTop(ListEntry entry){
            if(this.getScrollAmount() == 0){
                super.addEntryToTop(entry);
                this.setScrollAmount(0);
            } else {
                super.addEntryToTop(entry);
            }
        }

        public void remove(ListEntry listEntry){
            this.removeEntryWithoutScrolling(listEntry);
        }
    }

    public static class ListEntry extends ElementListWidget.Entry<ListEntry> {

        HashMap.Entry<String, Integer> entry;
        TextFieldWidget valueTextField;
        ButtonWidget removeButton;
        TextWidget itemName;
        MinecraftClient client = MinecraftClient.getInstance();
        HashMapList parentList;
        ItemStack representingItem;

        int screenWidth;

        public ListEntry(HashMap.Entry<String, Integer> entry, int screenWidth, HashMapList parentList){
            this.entry = entry;
            this.screenWidth = screenWidth;
            this.parentList = parentList;

            this.valueTextField = new TextFieldWidget(this.client.textRenderer, this.screenWidth / 2 + 5, 0, 35, 20, Text.literal("Value"));
            this.valueTextField.setText(String.valueOf(this.entry.getValue()));

            if(!Registries.ITEM.containsId(new Identifier(this.entry.getKey()))) {
                this.valueTextField.setEditable(false);
                this.valueTextField.setUneditableColor(0x444444);
                this.valueTextField.active = false;

                this.itemName = new TextWidget(0, 0, this.screenWidth / 2 - 30, 25, Text.literal(this.entry.getKey()), this.client.textRenderer);
                this.itemName.alignRight();
                this.itemName.setTextColor(0x444444);
                this.itemName.setTooltip(Tooltip.of(Text.translatable("configurable-item-despawn-duration.config.itemMissing")));
            } else {
                this.representingItem = new ItemStack(Registries.ITEM.get(new Identifier(this.entry.getKey())));

                this.itemName = new TextWidget(0, 0, this.screenWidth / 2 - 30, 25, Text.translatable(this.representingItem.getTranslationKey()), this.client.textRenderer);
                this.itemName.alignRight();

                this.valueTextField.setMaxLength(4);
                this.valueTextField.setTextPredicate(text -> {
                    int seconds;
                    try {
                        seconds = Integer.parseInt(text);
                    } catch(NumberFormatException e){
                        this.valueTextField.setEditableColor(0xFFFF7777);
                        this.valueTextField.setTooltip(Tooltip.of(Text.translatable("configurable-item-despawn-duration.config.notANumber")));

                        return true;
                    }

                    if(seconds > 1800){
                        this.valueTextField.setEditableColor(0xFFFF7777);
                        this.valueTextField.setTooltip(Tooltip.of(Text.translatable("configurable-item-despawn-duration.config.maximumValue", 1800).formatted(Formatting.RED)));
                    } else if(seconds < 0){
                        this.valueTextField.setEditableColor(0xFFFF7777);
                        this.valueTextField.setTooltip(Tooltip.of(Text.translatable("configurable-item-despawn-duration.config.minimumValue", 0).formatted(Formatting.RED)));
                    } else {
                        this.valueTextField.setEditableColor(-1);
                        this.valueTextField.setTooltip(null);

                        ConfigurableItemDespawnDurationConfig.despawnDurations.put(this.entry.getKey(), seconds);
                    }

                    return true;
                });
            }

            this.removeButton = new ButtonWidget.Builder(Text.literal("-"), (buttonWidget) -> {
                ConfigurableItemDespawnDurationConfig.despawnDurations.remove(this.entry.getKey());
                this.parentList.remove(this);
            }).dimensions(this.screenWidth / 2 + 55, 0, 20, 20).tooltip(Tooltip.of(Text.translatable("configurable-item-despawn-duration.config.remove"))).build();
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return this.children();
        }

        @Override
        public List<? extends ClickableWidget> children() {
            return List.of(this.valueTextField, this.removeButton, this.itemName);
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            for(ClickableWidget clickableWidget : this.children()){
                clickableWidget.setY(y);
                clickableWidget.render(context, mouseX, mouseY, tickDelta);
            }

            if(this.representingItem != null){
                context.drawItem(this.representingItem, this.screenWidth / 2 - 25, y + 2);
            }

            int textColor = this.representingItem == null ? 0x444444 : 0xFFFFFF;

            // The "s" after the text field to help indicate that it is in seconds
            context.drawText(this.client.textRenderer, "s", this.screenWidth / 2 + 42, y + 6, textColor, true);
        }
    }
}
