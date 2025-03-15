from nonebot.rule import to_me
from nonebot.plugin import on_command

weather = on_command("天气")

@weather.handle()
async def handle_function():
    await weather.finish("天气是...")
    pass  # do something here