from nonebot.plugin import on_fullmatch,on_regex
from nonebot.adapters import Bot, Event
from nonebot.typing import T_State
import time
import random
ban = on_regex("^(申请)?随机禁言$")
def rowBan ():
    rowA = random.randint(0, 1800)
    rowB = random.randint(0, 1440)
    return rowA*rowB

@ban.handle()
async def handle_function(event:Event,bot: Bot,state: T_State):
    user_id = event.user_id
    group_id = event.group_id
    # 检查 bot 是否有管理员权限
    try:
        member_info = await bot.get_group_member_info(group_id=group_id, user_id=bot.self_id)
        if member_info["role"] not in ["admin", "owner"]: 
            await ban.finish("no power")
            return
    except Exception:
        await ban.sefinishnd("no power")
        return
    try:
        sender_info = await bot.get_group_member_info(group_id=group_id, user_id=user_id)
        if sender_info["role"] in ["admin", "owner"]:# 管理员或群主
            await ban.finish("no power")
            return
    except Exception:
        await ban.finish("no power")
        return
    
    await bot.set_group_ban(
            group_id=group_id,
            user_id=user_id,
            duration=rowBan()
            )
    await ban.finish()
    pass  # do something here