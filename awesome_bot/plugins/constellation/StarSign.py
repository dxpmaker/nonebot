import requests
import random
import re
from datetime import datetime

class StarSign:
    def __init__(self):
        self.UrlList = [
            "https://www.xzw.com/fortune/aries/",
            "https://www.xzw.com/fortune/taurus/",
            "https://www.xzw.com/fortune/gemini/",
            "https://www.xzw.com/fortune/cancer/",
            "https://www.xzw.com/fortune/leo/",
            "https://www.xzw.com/fortune/virgo/",
            "https://www.xzw.com/fortune/libra/",
            "https://www.xzw.com/fortune/scorpio/",
            "https://www.xzw.com/fortune/sagittarius/",
            "https://www.xzw.com/fortune/capricorn/",
            "https://www.xzw.com/fortune/aquarius/",
            "https://www.xzw.com/fortune/pisces/"
        ]
        self.out = [
            "综合运势：", "爱情运势：", "事业学业：", "财富运势：", 
            "健康指数：", "商谈指数：", "幸运颜色：", "幸运数字：", 
            "速配星座：", "短评："
        ]
        self.StarSing = [
            "白羊", "金牛", "双子", "巨蟹", "狮子", 
            "处女", "天秤", "天蝎", "射手", "摩羯", 
            "水瓶", "双鱼"
        ]
        self.StarSingDate = [None] * 12
        self.DateNew = datetime.now().strftime('%Y-%m-%d')
        self.ran = random.Random()
        self.time = int(datetime.now().replace(hour=0, minute=0, second=0, microsecond=0).timestamp())
        self.new_star_sign()

    def new_time(self):
        if self.DateNew != datetime.now().strftime('%Y-%m-%d'):
            self.DateNew = datetime.now().strftime('%Y-%m-%d')
            self.time = int(datetime.now().replace(hour=0, minute=0, second=0, microsecond=0).timestamp())
            self.new_star_sign()

    def msg_to_str(self, msg):
        if self.DateNew != datetime.now().strftime('%Y-%m-%d'):
            self.new_time()
        for i, star in enumerate(self.StarSing):
            if re.match(f"{star}(今日)?运势", msg):
                return f"{msg}\n{self.StarSingDate[i]}"
        return "没有这个星座哦"

    def string_to_int(self, str):
        if str in self.StarSing:
            return self.StarSing.index(str)
        return -1

    def new_star_sign(self):
        for i in range(len(self.UrlList)):
            for _ in range(3):
                try:
                    self.StarSingDate[i] = self.get_new_star_sign(i)
                    break
                except requests.RequestException:
                    continue
            if self.StarSingDate[i] is None:
                raise RuntimeError("Failed to fetch star sign data")

    def new_star_sign_by_name(self, str):
        i = self.string_to_int(str)
        if i != -1:
            try:
                self.StarSingDate[i] = self.get_new_star_sign(i)
            except requests.RequestException:
                pass

    def get_new_star_sign(self, i):
        return self.luck_url_to_str(self.get(self.UrlList[i]))

    def luck_url_to_str(self, str):
        datelist = self.regular(str, r'<li( class="desc")?><label>[\s\S]+?</li>')
        put_str = ""
        for i in range(4):
            put_str += f"{self.out[i]}{self.to_xin(self.to_num(datelist[i]) // 16)}\n"
        for i in range(4, len(datelist) - 1):
            put_str += f"{self.out[i]}{self.regular(datelist[i], r'(?<=</label>)[\s\S]+?(?=</li>)')[0]}\n"
        put_str += f"{self.out[-1]}{self.regular(datelist[-1], r'(?<=</label>).+?(?=</li>)')[0]}"
        return put_str

    def regular(self, str, regstr):
        return re.findall(regstr, str)

    def to_xin(self, n):
        return "★" * n + "☆" * (5 - n)

    def to_num(self, str):
        return int(self.regular(str, r"\d+")[0])

    def get(self, url):
        response = requests.get(url)
        if response.status_code != 200:
            return ""
        return response.text


# Example usage
# star_sign = StarSign()
# print(star_sign.luck(1234567890))  # Example QQ ID
# print(star_sign.msg_to_str("白羊座今日运势"))  # Example star sign message
