from nonebot.adapters.onebot.v11 import PrivateMessageEvent, GroupMessageEvent
from nonebot.plugin import on_fullmatch
matcher = on_fullmatch("/test")
@matcher.handle()
async def handle_private(event: PrivateMessageEvent):
    await matcher.finish("私聊消息")

@matcher.handle()
async def handle_group(event: GroupMessageEvent):
    await matcher.finish("群聊消息")