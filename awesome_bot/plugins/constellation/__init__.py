from nonebot import on_endswith
from nonebot.adapters import Event,Message
from nonebot.params import RawCommand
# from awesome_bot.plugins.constellation.StarSign import msg_to_str
constellation = on_endswith(({"座今日运势","座运势"}), ignorecase=False)
@constellation.handle()
async def _(args: Message = RawCommand()):
    if location := args.extract_plain_text():
        await constellation.finish(f"{location}")
    else:
        await constellation.finish("没有星座哦")