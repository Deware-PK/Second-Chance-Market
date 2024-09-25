package com.github.dewarepk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class ItemPool {

    private static final ItemPool instance = new ItemPool();

    private final ArrayList<ItemData> items = new ArrayList<>();

    private ItemPool() {
        registerDefault();
    }

    public void addItem(ItemData item) {
        items.add(item);
    }

    public void addItem(String title, String pictureUrl, double price, ItemType type, String... description) {
        ItemData item = new ItemData(title, pictureUrl, price, type, description);
        items.add(item);
    }

    public void deleteItem(UUID uuid) {
        for (ItemData item : items) {
            if (item.getUuid().equals(uuid)) {
                items.remove(item);
                return;
            }
        }
    }

    public void deleteItem(ItemData item) {
        items.remove(item);
    }


    public List<ItemData> getItems() {
        return Collections.unmodifiableList(items);
    }

    public ItemData getItemByUuid(UUID uuid) {

        for (ItemData item : this.items)
            if (item.getUuid().equals(uuid))
                return item;

        return null;
    }

    public List<ItemData> getItemByType(ItemType type) {
        List<ItemData> byTypes = new ArrayList<>();

        for (ItemData item : this.items) {
            if (item.getType().equals(type)) {
                byTypes.add(item);
            }
        }

        return byTypes;
    }


    public static ItemPool getInstance() {
        return instance;
    }

    private void registerDefault() {
        items.add(new ItemData("Butterfly Knife Trainer",
                "https://m.media-amazon.com/images/I/517gZhu4wxS.jpg",
                180,
                ItemType.TOOL,
                "【Non Offensive Blade 】 The unsharpened blade of our practice butterfly knife will keep you away from danger as a beginner.This butterfly trainer is perfect for learning how to use the real thing without all the cuts and bruising to the hands.\n",
                "【Premium Quality】 Durably built and sleek in appearance, This knives give you the confidence to practice without fear. Our carefully designed butterfly knife trainers have a solid pin construction and copper washer setup with a spring latch to ensure these knives are made to last!\n",
                "【Perfect Balance】 We calculate a perfect weight balance of the butterfly knife trainer and a perfect center of mass to make sure you can flip it smoothly.Suitable for a variety of fancy action.This is a perfect product if you are a butterfly knives lover."));

        items.add(new ItemData("SpongeBob Chair",
                "https://www.deltachildren.com/cdn/shop/products/y8sklpkrls44udnvnrpd.jpg?v=1614635808",
                3925.11,
                ItemType.FURNITURE,
                "Oh Buoy, you know it's going to be the best day ever when your child is relaxing in this SpongeBob",
                "SquarePants High Back Upholstered Chair by Delta Children! An easy way to bring SpongeBob's bubbly",
                "personality to your little one's space, this cozy kids' chair features his big yellow face, a high,",
                "supportive back, plenty of padding and easy-to-clean faux leather upholstery."));

        items.add(new ItemData("iPhone 16 Pro Max",
                "https://media.studio7thailand.com/154749/iPhone_16_Pro_Max_Desert_Titanium_PDP_Image_Position_1a_Desert_Titanium_Color__TH-TH.jpg",
                64900,
                ItemType.DIGITAL,
                "Just try to scam"));

        items.add(new ItemData("Women Shirt",
                "https://inwfile.com/s-a/7gbyac.jpg",
                580,
                ItemType.CLOTHE,
                "เสื้อเชิ้ตผู้หญิง แขนยาว เสื้อเชิ้ต สีขาว แฟชั่น ใส่ทำงาน แบบมีผ้าคล้องคอ แบบเนคไท เก๋ ๆ"));

        items.add(new ItemData("Man Shirt",
                "https://img.lazcdn.com/g/p/9fad83ebd4255378a142f1555b3f42f5.png_720x720q80.png",
                480,
                ItemType.CLOTHE,
                "เสื้อเชิ้ตผู้ชายฤดูร้อนหลวมบางผ้าไหมน้ำแข็งแขนสั้นห้าจุดแขนเสื้อผู้ชายเกาหลีลำลองสีดำ เชิ้ต เสื้อเชิตชาย2022"));

        items.add(new ItemData("iPad Pro M4",
                "https://www.istudio.store/cdn/shop/files/iPad_Pro_13_M4_Cellular_Space_Black_PDP_Image_Position_1b__en-US_9360f880-16b2-4976-97d8-03f8712c2716.jpg?v=1716468976&width=823",
                67900,
                ItemType.DIGITAL,
                "iPad Pro ใหม่นั้นบางเหลือเชื่อ โดยมาพร้อมประสิทธิภาพที่แรงสุดพลังของชิป Apple M4, จอภาพ Ultra Retina XDR สุดล้ำ รวมถึง Wi-Fi 6E และ 5G ที่เร็วสุดแรง พร้อมด้วย Apple Pencil Pro และ Magic Keyboard ซึ่งนำความอเนกประสงค์ ความคิดสร้างสรรค์ และประสิทธิภาพการทำงานที่ไม่มีที่สิ้นสุดมาสู่ปลายนิ้วคุณ"));

        items.add(new ItemData("Apple Watch Ultra 2",
                "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/MXKF3ref_VW_34FR+watch-case-49-titanium-black-ultra2_VW_34FR+watch-face-49-milanese-ultra2_VW_34FR_GEO_TH_LANG_TH?wid=750&hei=712&trim=1%2C0&fmt=p-jpg&qlt=95&.v=1724807163371",
                33900,
                ItemType.DIGITAL,
                "ตัวเรือนไทเทเนียม สีดำ | สายแบบ Milanese Loop ไทเทเนียม"));

        items.add(new ItemData("สว่านไฟฟ้าไร้สาย 3 ระบบ OSUKA รุ่น OCD-40-S2",
                "https://image.makewebeasy.net/makeweb/0/OmdeorHQh/OSUKA/%E0%B8%AA%E0%B8%A7%E0%B9%88%E0%B8%B2%E0%B8%99%E0%B9%84%E0%B8%9F%E0%B8%9F%E0%B9%89%E0%B8%B2%E0%B9%84%E0%B8%A3%E0%B9%89%E0%B8%AA%E0%B8%B2%E0%B8%A2_3_%E0%B8%A3%E0%B8%B0%E0%B8%9A%E0%B8%9A_OSUKA_%E0%B8%A3%E0%B8%B8%E0%B9%88%E0%B8%99_OCD_40_S2.webp?v=202405291424",
                1550,
                ItemType.TOOL,
                "คุณสมบัติ สว่านไฟฟ้าไร้สาย 3 ระบบ OSUKA รุ่น OCD-40-S2 แรงบิดสูงสุด: 30 นิวตันเมตร ความเร็วรอบตัวเปล่า: 0-1,350 รอบ/นาที ความเร็วรอบกระแทก: 0-30,000 รอบ/นาที ขนาดปากจับ: 0.8-10 มิลลิเมตร น้ำหนัก: 1.7 กิโลกรัม ระบบ : 3 ระบบ (ขัน, เจาะ, เจาะกระแทก)"));

        items.add(new ItemData("Hammer",
                "https://ktw.co.th/medias/sys_master/SAP-L01/SAP-L01/h10/hda/9807163457566/-TRUPER-T441-10560-1.jpg",
                324,
                ItemType.TOOL,
                "รายละเอียดสินค้า",
                "• เครื่องมือที่จะช่วยให้คุณตอกหรือถอนตะปูได้อย่างมืออาชีพ",
                "ออกแบบมาให้สามารถใช้งานได้อย่างคล่องตัว",
                "หยิบจับได้อย่างถนัดมือ",
                "• เพื่อความสะดวกในการทำงาน",
                "เหมาะสำหรับการประกอบติดตั้งและรื้อถอนชิ้นส่วนต่าง ๆ ไม่ว่าจะเป็น",
                "เฟอร์นิเจอร์ งานไม้ หรืออะไหล่โลหะ ก็สามารถปรับแก้ได้อย่างง่ายดาย",
                "• หัวเหล็กคาร์บอนสูงขึ้นรูปด้วยการขัดกึ่งเงา",
                "• ด้ามไม้โอ๊คเคลือบและแว็กซ์",
                "อัดแน่นด้วยลิ่มที่ช่วยยึดข้อต่อได้อย่างสมบูรณ์แบบ",
                "• เหมาะสำหรับงานช่างไม้, บ้าน, ฯลฯ"));

        items.add(new ItemData("Wardrobe",
                "https://www.konceptfurniture.com/media/wysiwyg/MB.jpg",
                10400,
                ItemType.FURNITURE,
                "Koncept Furniture เฟอร์นิเจอร์ ตู้เสื้อผ้า Blox สีครีมลินิน ตัด ไวท์ลินิน สไตล์โมเดิร์น ขนาด 100 ซม.(W100 x D59 x H210 cm.) Body ชั้นแบ่ง (Op B) หน้าบานกระจกใส มาพร้อมไฟ LED ที่ช่วยให้ตู้ของคุณสว่าง หาเสื้อผ้าและสิ่งของได้ง่ายขึ้น ฟังก์ชั่นครบครัน สะท้อนไลฟ์สไตล์ได้อย่างสมบูรณ์แบบ (ราคาดังกล่าวไม่รวมเครื่องใช้ไฟฟ้า และอุปกรณ์ตกแต่ง)"));

        items.add(new ItemData("Mirror",
                "https://inwfile.com/s-db/xqmqcj.jpg",
                6800,
                ItemType.FURNITURE,
                "เช็กความเรียบร้อยของร่างกายด้วย กระจกยาวตั้งพื้น จาก MOYA ให้เงาสะท้อนที่สมจริง มองเห็นภาพได้อย่างชัดเจนด้วยผิวกระจกที่เรียบเนียน พร้อมกรอบที่ผ่านการออกแบบให้โค้งมนสวยงาม ประกอบกับขาตั้งที่แข็งแรง สามารถจัดวางได้ทุกมุมบ้าน"));

        items.add(new ItemData("LEGO Ninjago",
                "https://inwfile.com/s-di/vc3yyv.jpg",
                1310,
                ItemType.TOY,
                "The building set features 9 minifigures for playing out Prime Empire action, including NINJAGO Digi Jay, Avatar Nya and Avatar Cole, plus exclusive figures of Avatar Pink Zane and Harumi!"));

        items.add(new ItemData("Rubik Gan356",
                "https://fh.lnwfile.com/_/fh/_raw/wp/nn/6q.jpg",
                1490,
                ItemType.TOY,
                "รูบิคที่มีน้ำหนักเบา ทั้งที่มีระบบแม่เหล็ก เล่นลื่น โดยไม่ต้องปรับแต่งใดใด เล่นลื่น เล่น สนุกเป็นรูบิคที่มีคุณภาพสูงชิ้นส่วนรูบิค ออกแบบด้วย Honeycomb design"));

        items.add(new ItemData("Yamaha FX310AII",
                "https://inwfile.com/s-du/gr3ln1.jpg",
                9000,
                ItemType.INSTRUMENT,
                "Yamaha FX310AII กีต้าร์โปร่งไฟฟ้าคุณภาพจากค่ายดัง รุ่นนี้มาพร้อมปิ๊กอัพไฟฟ้าที่ตอบสนองต่อความต้องการในเรื่องของการต่อแอมป์เพิ่มเติมสำหรับคนที่ต้องการใช้ในงานเลี้ยงต่างๆ ด้วยไม้หน้าที่ใช้ไม้สปรู๊ซตระกูลสนที่ให้เสียงโทนใสอยู่แล้ว ด้าานข้างเเละหลังทำจากไม้เมรานตีที่คุมโทนเสียงให้มีความหนานุ่ม มาพร้อมลูกบิดชุบโครเมี่ยมให้สวยงามอย่างดี ดีไซด์เรียบหรูแบบตะวันตก แถมราคายังคุ้มค่าอีกด้วย เรียกได้ว่าตัวเดียวได้ครบทั้งเรื่องย่านเสียง ราคาเเละวัสดุอย่างดีอีกด้วย"));

        items.add(new ItemData("Electaic Guitar",
                "https://goodnotemusicth.com/wp-content/uploads/2021/02/yamaha-RGX-220-DZ-01.jpg",
                17500,
                ItemType.INSTRUMENT,
                "กีตาร์ไฟฟ้า ทรง Modern Strat ปิ๊กอัพฮัมบัคกิ้งคู่แบบเซรามิค เหมาะสำหรับผู้เล่นแนว Rock – Metal บอดี้ทำจาไม้โซลิดเอลเดอร์ เคลือบด้าน"));
    }
}
