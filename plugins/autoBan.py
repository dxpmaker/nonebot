from nonebot.plugin import on_fullmatch
from nonebot.adapters import Bot, Event
luck = on_fullmatch("随机禁言")
import time
import random

def rowBan (QQID:int):
    return "今日运气指数为"+str(random.randint(1, 1000))+" 悲剧指数为"+str(random.randint(1000, 2000))

@luck.handle()
async def handle_function(event:Event):

    await luck.finish(int(random.randint(0, 2592000)))
    pass  # do something here