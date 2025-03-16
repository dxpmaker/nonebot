from nonebot.plugin import on_fullmatch
from nonebot.adapters import Bot, Event
import time
import random
from datetime import datetime
luck = on_fullmatch("欧非鉴定")
def Luck (QQID:int):
    today = datetime.today()
    midnight = datetime(today.year, today.month, today.day)
    timestamp = int(midnight.timestamp())
    random.seed(timestamp+QQID)
    return "今日运气指数为"+str(random.randint(1, 1000))+" 悲剧指数为"+str(random.randint(1000, 2000)-1000)

@luck.handle()
async def handle_function(event:Event):
    await luck.finish(Luck(int(event.get_user_id())))
    pass  # do something here