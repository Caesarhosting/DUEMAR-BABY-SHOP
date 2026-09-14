package com.example.data

import androidx.compose.ui.graphics.Color
import com.example.model.BabyProduct
import com.example.model.Model3DType
import com.example.model.ProductCategory
import com.example.model.ProductColorway
import com.example.model.ProductSpecification

object ProductCatalog {

    val products: List<BabyProduct> = listOf(
        // 1. BANTAL SOFA BAYI BIASA
        BabyProduct(
            id = "prod_sofa_biasa",
            name = "Bantal Sofa Bayi Biasa (Baby Lounger)",
            category = ProductCategory.TIDUR,
            price = 48000,
            originalPrice = 68000,
            rating = 4.9f,
            reviewCount = 542,
            soldCount = 1820,
            shortDescription = "Kasur sofa santai bayi ergonomis dengan cekungan anti gumoh dan safety belt aman.",
            fullDescription = "Bantal Sofa Bayi Biasa dari Duemar Baby Shop dirancang dengan sudut kemiringan ideal 30 derajat untuk mencegah bayi gumoh setelah menyusui. Dilengkapi strap pengaman sabuk pinggang, isian dakron silikon grade A yang empuk dan tidak mudah kempes, serta sarung katun CVC yang sejuk di kulit sensitif bayi.",
            modelType = Model3DType.SOFA_BIASA,
            isBestSeller = true,
            isMurahEfisien = true,
            colorways = listOf(
                ProductColorway("Baby Blue Clouds", Color(0xFF60A5FA), "Awan Biru", "#60A5FA"),
                ProductColorway("Pastel Pink Bunny", Color(0xFFF472B6), "Kelinci Pink", "#F472B6"),
                ProductColorway("Mint Green Safari", Color(0xFF34D399), "Safari Mint", "#34D399"),
                ProductColorway("Warm Oat Bear", Color(0xFFFBBF24), "Beruang Oatmeal", "#FBBF24")
            ),
            specifications = listOf(
                ProductSpecification("Ukuran", "65 x 65 x 18 cm"),
                ProductSpecification("Bahan Luar", "100% Katun CVC Grade A (Halus & Dingin)"),
                ProductSpecification("Isian", "100% Dakron Silikon Murni (Hypoallergenic)"),
                ProductSpecification("Fitur Khusus", "Safety Lock Strap, Handle Jinjing Praktis"),
                ProductSpecification("Usia", "0 - 18 Bulan")
            ),
            features = listOf(
                "Mencegah gumoh & refluks asam lambung",
                "Desain cekungan ergonomis menjaga postur tulang belakang",
                "Dapat dicuci langsung (mesin cuci mode gentle)",
                "Ringan dan efisien untuk dibawa bepergian"
            )
        ),

        // 2. BANTAL SOFA BAYI DOUBLE
        BabyProduct(
            id = "prod_sofa_double",
            name = "Bantal Sofa Bayi Double (2-Tier Deluxe)",
            category = ProductCategory.TIDUR,
            price = 85000,
            originalPrice = 115000,
            rating = 5.0f,
            reviewCount = 418,
            soldCount = 980,
            shortDescription = "Sofa bayi dua lapis ekstra tebal dengan sandaran kepala bertingkat & sabuk 3-titik.",
            fullDescription = "Varian premium berukuran lebih besar dengan ketebalan ganda (double tier cushion) untuk kenyamanan maksimal bayi Anda. Lapisan ganda memberikan bantalan ekstra saat bersandar, dilengkapi harness pengaman 3-titik dan kain katun Jepang super lembut.",
            modelType = Model3DType.SOFA_DOUBLE,
            isBestSeller = true,
            isMurahEfisien = true,
            colorways = listOf(
                ProductColorway("Sky Ocean Duo", Color(0xFF38BDF8), "Kombinasi Biru Langit", "#38BDF8"),
                ProductColorway("Soft Peach Rose", Color(0xFFFB7185), "Peach Blossom", "#FB7185"),
                ProductColorway("Sage Forest", Color(0xFF2DD4BF), "Sage Mint Daun", "#2DD4BF"),
                ProductColorway("Vanilla Cream", Color(0xFFFDE68A), "Krem Vanilla Polka", "#FDE68A")
            ),
            specifications = listOf(
                ProductSpecification("Ukuran", "72 x 70 x 22 cm"),
                ProductSpecification("Ketebalan", "Double Layer Busa Padat + Silikon 1200gr"),
                ProductSpecification("Bahan Luar", "Katun Jepang Premium Oeko-Tex"),
                ProductSpecification("Fitur Khusus", "3-Point Safety Belt, Dual Side Usable"),
                ProductSpecification("Usia", "0 - 24 Bulan")
            ),
            features = listOf(
                "Lapisan ganda lebih empuk dan tahan beban hingga 18kg",
                "Dua sisi motif berbeda (bisa dibolak-balik)",
                "Sandaran leher lebih tinggi mencegah leher kaku",
                "Garansi jahitan rapi kuat standar Duemar"
            )
        ),

        // 3. BANTAL SET
        BabyProduct(
            id = "prod_bantal_set",
            name = "Bantal Set Bayi (1 Bantal Peyang + 2 Guling)",
            category = ProductCategory.TIDUR,
            price = 58000,
            originalPrice = 78000,
            rating = 4.8f,
            reviewCount = 380,
            soldCount = 1420,
            shortDescription = "Set bantal anti kepala peyang mahkota dan sepasang guling lembut dengan tali pita.",
            fullDescription = "Paket perlengkapan tidur esensial terdiri dari 1 bantal peyang crown/mahkota dengan lekukan khusus untuk membentuk kepala bulat sempurna, ditambah 2 guling pendamping untuk menjaga posisi tidur bayi agar tidak mudah terbalik.",
            modelType = Model3DType.BANTAL_SET,
            isBestSeller = false,
            isMurahEfisien = true,
            colorways = listOf(
                ProductColorway("Pastel Polka Blue", Color(0xFF60A5FA), "Polkadot Biru", "#60A5FA"),
                ProductColorway("Baby Candy Pink", Color(0xFFF472B6), "Permen Pink", "#F472B6"),
                ProductColorway("Starry Grey", Color(0xFF94A3B8), "Bintang Abu Modern", "#94A3B8")
            ),
            specifications = listOf(
                ProductSpecification("Isi Paket", "1 Bantal Peyang + 2 Guling Bayi"),
                ProductSpecification("Ukuran Bantal", "30 x 25 cm (cekungan 8 cm)"),
                ProductSpecification("Ukuran Guling", "Panjang 45 cm, Diameter 12 cm"),
                ProductSpecification("Bahan", "Katun Halus CVC Anti-Alergi"),
                ProductSpecification("Isian", "Microfiber Silikon Padat Lembut")
            ),
            features = listOf(
                "Mencegah flat-head syndrome (kepala datar)",
                "Sarung guling bisa dilepas dan dicuci terpisah",
                "Tali serut pita guling aman tanpa resleting yang tajam"
            )
        ),

        // 4. BEDCOVER SET
        BabyProduct(
            id = "prod_bedcover_set",
            name = "Bedcover Set Bayi Lengkap + Bantal Guling",
            category = ProductCategory.TIDUR,
            price = 235000,
            originalPrice = 285000,
            rating = 4.9f,
            reviewCount = 290,
            soldCount = 610,
            shortDescription = "Set bedcover tebal empuk quilt motif, sprei kasur, 1 bantal rumbai & 2 guling.",
            fullDescription = "Perlengkapan tempat tidur bayi komplit paling mewah dengan harga termurah di kelasnya. Terdiri dari bedcover tebal bermotif quilting kotak modern, sprei sudut karet elastis, bantal bayi berenda rumbai mewah, dan dua guling lembut.",
            modelType = Model3DType.BEDCOVER_SET,
            isBestSeller = true,
            isMurahEfisien = false,
            colorways = listOf(
                ProductColorway("Nordic Blue Animals", Color(0xFF0284C7), "Hewan Biru Nordic", "#0284C7"),
                ProductColorway("Pastel Floral Blush", Color(0xFFFB7185), "Bunga Pastel Soft", "#FB7185"),
                ProductColorway("Safari Jungle Green", Color(0xFF0D9488), "Hutan Safari Tropis", "#0D9488")
            ),
            specifications = listOf(
                ProductSpecification("Ukuran Bedcover", "110 x 90 cm"),
                ProductSpecification("Ukuran Bantal Rumbai", "35 x 25 cm"),
                ProductSpecification("Ukuran Guling (2 pcs)", "Panjang 50 cm"),
                ProductSpecification("Bahan Kain", "100% Katun Viscose Dingin Lembut"),
                ProductSpecification("Isian Bedcover", "Silikon Lembaran HCS 8oz Anti Menggumpal")
            ),
            features = listOf(
                "Jahitan quilt diamond elegan tidak gampang bergeser",
                "Sangat empuk bisa sekaligus jadi matras bermain di lantai",
                "Kemasan tas mika tebal eksklusif cocok untuk kado lahiran"
            )
        ),

        // 5. BEDCOVER SINGLE
        BabyProduct(
            id = "prod_bedcover_single",
            name = "Bedcover Single Bayi Lembut (Selimut Tebal)",
            category = ProductCategory.TIDUR,
            price = 125000,
            originalPrice = 165000,
            rating = 4.8f,
            reviewCount = 210,
            soldCount = 740,
            shortDescription = "Selimut bedcover tebal single multifungsi dengan bahan katun adem dan jahitan piping.",
            fullDescription = "Bedcover satuan serbaguna yang bisa digunakan sebagai selimut tidur hangat ber-AC, alas tidur saat traveling, atau matras bermain tummy-time bayi. Sangat praktis, awet dicuci berulang kali tanpa susut.",
            modelType = Model3DType.BEDCOVER_SINGLE,
            isBestSeller = false,
            isMurahEfisien = true,
            colorways = listOf(
                ProductColorway("Cloud Sky Blue", Color(0xFF38BDF8), "Awan Langit Biru", "#38BDF8"),
                ProductColorway("Strawberry Milk Pink", Color(0xFFF472B6), "Strawberry Pink", "#F472B6"),
                ProductColorway("Golden Honey Bee", Color(0xFFF59E0B), "Lebah Manis Kuning", "#F59E0B")
            ),
            specifications = listOf(
                ProductSpecification("Ukuran", "105 x 85 cm"),
                ProductSpecification("Bahan", "Katun CVC Halus Serat Alami"),
                ProductSpecification("Ketebalan", "Silikon 6oz Ringan & Hangat"),
                ProductSpecification("Finishing", "Piping Keliling Kuat & Rapi")
            ),
            features = listOf(
                "Kehangatan optimal tanpa membuat bayi berkeringat",
                "Mudah dilipat dan disimpan dalam diaper bag",
                "Dicuci cepat kering dan tidak berbulu"
            )
        ),

        // 6. BANTAL MENYUSUI
        BabyProduct(
            id = "prod_bantal_menyusui",
            name = "Bantal Menyusui Ergonomis (Nursing Pillow)",
            category = ProductCategory.TIDUR,
            price = 68000,
            originalPrice = 95000,
            rating = 4.9f,
            reviewCount = 612,
            soldCount = 1530,
            shortDescription = "Bantal U-shape penopang punggung ibu dan bayi saat menyusui agar tidak pegal.",
            fullDescription = "Sahabat terbaik bagi bunda yang sedang menyusui! Bantal dengan lekukan U ergonomis yang pas melingkar di pinggang ibu, menopang bobot bayi sehingga pundak dan punggung tidak mudah lelah. Juga sangat ideal untuk latihan duduk si kecil.",
            modelType = Model3DType.BANTAL_MENYUSUI,
            isBestSeller = true,
            isMurahEfisien = true,
            colorways = listOf(
                ProductColorway("Mint Dinosaur", Color(0xFF10B981), "Dinosaurus Mint", "#10B981"),
                ProductColorway("Blush Pink Bow", Color(0xFFF472B6), "Pita Pink Manis", "#F472B6"),
                ProductColorway("Baby Blue Whale", Color(0xFF0284C7), "Paus Biru Lucu", "#0284C7")
            ),
            specifications = listOf(
                ProductSpecification("Ukuran", "60 x 50 x 18 cm"),
                ProductSpecification("Bahan Sarung", "Katun Lembut Beresleting (Bisa Dicuci)"),
                ProductSpecification("Bahan Bantal", "Kain Furing Katun Isi Dakron HCS"),
                ProductSpecification("Fitur", "Gesper Klip Pinggang Fleksibel")
            ),
            features = listOf(
                "Meringankan beban tangan dan punggung ibu hingga 80%",
                "Sarung bisa dilepas pasang dengan mudah untuk dicuci",
                "Multifungsi: Penopang menyusui, sandaran tummy time, dan ganjal belajar duduk"
            )
        ),

        // 7. KACAMATA BAYI
        BabyProduct(
            id = "prod_kacamata_bayi",
            name = "Kacamata Bayi UV Protection + Tali Lentur",
            category = ProductCategory.AKSESORI,
            price = 28000,
            originalPrice = 45000,
            rating = 4.7f,
            reviewCount = 345,
            soldCount = 2100,
            shortDescription = "Kacamata anti sinar UV400 frame silikon lentur anti patah dengan strap kepala adjustable.",
            fullDescription = "Perlindungan maksimal untuk mata bayi saat berjemur pagi hari atau jalan-jalan outdoor. Dibuat dari material food-grade silicone TPEE yang ultra fleksibel, bisa ditekuk 360 derajat tanpa patah. Lensa polarized UV400 melindungi mata sensitif dari radiasi matahari.",
            modelType = Model3DType.KACAMATA_BAYI,
            isBestSeller = true,
            isMurahEfisien = true,
            colorways = listOf(
                ProductColorway("Cyan Blue Ocean", Color(0xFF06B6D4), "Biru Cyan Terang", "#06B6D4"),
                ProductColorway("Candy Blossom Pink", Color(0xFFEC4899), "Pink Permen", "#EC4899"),
                ProductColorway("Lemon Yellow Bright", Color(0xFFEAB308), "Kuning Lemon", "#EAB308"),
                ProductColorway("Matte Black Cool", Color(0xFF1E293B), "Hitam Elegan", "#1E293B")
            ),
            specifications = listOf(
                ProductSpecification("Material Frame", "Food-Grade TPEE Silicone (Bebas BPA, Anti Patah)"),
                ProductSpecification("Lensa", "Polarized TAC UV400 Filter"),
                ProductSpecification("Tali Strap", "Karet Elastis Neoprene Lembut"),
                ProductSpecification("Usia Rekomendasi", "0 - 36 Bulan")
            ),
            features = listOf(
                "Perlindungan sinar UV 100% saat berjemur pagi",
                "Frame super elastis, aman digigit dan dibengkokkan",
                "Tali strap adjustable tidak mudah lepas saat bayi aktif bergerak"
            )
        ),

        // 8. BAK MANDI LIPAT BAYI ERGONOMIS
        BabyProduct(
            id = "prod_bak_mandi",
            name = "Bak Mandi Lipat Bayi Ergonomis + Drain Sensor",
            category = ProductCategory.MANDI,
            price = 145000,
            originalPrice = 195000,
            rating = 4.9f,
            reviewCount = 189,
            soldCount = 520,
            shortDescription = "Bak mandi lipat hemat tempat dengan sensor suhu air dan kaki anti-slip kokoh.",
            fullDescription = "Solusi praktis dan hemat tempat untuk memandikan bayi dengan nyaman. Bisa dilipat rata hingga tebal 7 cm saja sehingga mudah disimpan atau digantung. Dilengkapi lubang pembuangan air dengan penutup silikon yang berubah warna jika air terlalu panas.",
            modelType = Model3DType.BAK_MANDI,
            isBestSeller = false,
            isMurahEfisien = true,
            colorways = listOf(
                ProductColorway("Pastel Aquamarine", Color(0xFF38BDF8), "Biru Mint Pastel", "#38BDF8"),
                ProductColorway("Blush Coral Pink", Color(0xFFFB7185), "Pink Coral Lembut", "#FB7185"),
                ProductColorway("Avocado Fresh Green", Color(0xFF34D399), "Hijau Alpukat", "#34D399")
            ),
            specifications = listOf(
                ProductSpecification("Ukuran Terbuka", "82 x 48 x 22 cm"),
                ProductSpecification("Ukuran Terlipat", "82 x 48 x 7 cm"),
                ProductSpecification("Bahan", "PP + TPE Elastomer Tahan Panas Bebas Racun"),
                ProductSpecification("Kapasitas", "35 Liter / Beban hingga 30 kg"),
                ProductSpecification("Fitur Suhu", "Plug Sensor Panas Otomatis (Berubah Putih > 38°C)")
            ),
            features = listOf(
                "Hemat tempat 80%, cocok untuk rumah minimalis atau bepergian",
                "Sensor indikator air panas menjaga keselamatan kulit bayi",
                "Kaki penyangga berkunci ganda anti goyang"
            )
        ),

        // 9. HANDUK BAYI MICROFIBER ULTRA-SOFT
        BabyProduct(
            id = "prod_handuk_bayi",
            name = "Handuk Bayi Bulu Microfiber Ultra-Soft Berkarakter",
            category = ProductCategory.MANDI,
            price = 32000,
            originalPrice = 48000,
            rating = 4.9f,
            reviewCount = 520,
            soldCount = 3100,
            shortDescription = "Handuk bulu super lembut berdaya serap air 7x lebih tinggi, aman untuk kulit bayi baru lahir.",
            fullDescription = "Handuk bayi dengan bulu coral velvet microfluffy yang sangat halus seperti sutra. Mampu mengeringkan tubuh bayi dalam hitungan detik tanpa perlu digosok keras, mencegah iritasi kemerahan pada kulit bayi baru lahir.",
            modelType = Model3DType.GENERIC_CUSHION,
            isBestSeller = true,
            isMurahEfisien = true,
            colorways = listOf(
                ProductColorway("Blue Teddy Bear", Color(0xFF60A5FA), "Biru Beruang", "#60A5FA"),
                ProductColorway("Pink Sweet Rabbit", Color(0xFFF472B6), "Pink Kelinci", "#F472B6"),
                ProductColorway("Cream Happy Duck", Color(0xFFFBBF24), "Krem Bebek", "#FBBF24")
            ),
            specifications = listOf(
                ProductSpecification("Ukuran", "70 x 140 cm (Jumbo)"),
                ProductSpecification("Material", "Coral Fleece Microfiber High Density"),
                ProductSpecification("Ketebalan", "Gramasi 380 GSM"),
                ProductSpecification("Sertifikasi", "Bebas Fluorescent & Kimia Berbahaya")
            ),
            features = listOf(
                "Daya serap luar biasa tinggi (menyerap air instan)",
                "Bulu tidak mudah rontok atau menempel di kulit bayi",
                "Mudah dicuci dan tetap lembut meski sering dipakai"
            )
        ),

        // 10. JARING & SPONGE MANDI BAYI
        BabyProduct(
            id = "prod_jaring_mandi",
            name = "Jaring Mandi Bayi Anti-Slip + Busa Penopang",
            category = ProductCategory.MANDI,
            price = 25000,
            originalPrice = 38000,
            rating = 4.8f,
            reviewCount = 270,
            soldCount = 1890,
            shortDescription = "Alas jaring penopang bayi di bak mandi dengan 5 pengait sabuk aman.",
            fullDescription = "Membantu bunda memandikan bayi sendirian dengan aman dan tenang tanpa takut tergelincir. Dilengkapi bantalan busa empuk penopang leher dan kepala bayi.",
            modelType = Model3DType.GENERIC_CUSHION,
            isBestSeller = false,
            isMurahEfisien = true,
            colorways = listOf(
                ProductColorway("Pastel Sky", Color(0xFF38BDF8), "Biru Cerah", "#38BDF8"),
                ProductColorway("Pastel Peach", Color(0xFFFB7185), "Peach", "#FB7185")
            ),
            specifications = listOf(
                ProductSpecification("Tipe", "Jaring Mandi Suspensi 5 Titik"),
                ProductSpecification("Material", "Mesh Breathable 3D + Busa Terapung"),
                ProductSpecification("Kompatibilitas", "Cocok untuk semua jenis bak mandi")
            ),
            features = listOf(
                "Panjang tali pengait dapat diatur sesuai ukuran bak",
                "Bantalan kepala empuk menjaga telinga dari kemasukan air",
                "Cepat kering dan anti jamur"
            )
        ),

        // 11. SET JUMPER BAYI ORGANIC COTTON
        BabyProduct(
            id = "prod_jumper_bayi",
            name = "Set Jumper Bayi Katun Organic 3-in-1 Motif Lucu",
            category = ProductCategory.PAKAIAN,
            price = 69000,
            originalPrice = 95000,
            rating = 4.9f,
            reviewCount = 430,
            soldCount = 1670,
            shortDescription = "Set isi 3 pcs jumper romper bayi bahan 100% katun combed 30s adem bersertifikat SNI.",
            fullDescription = "Paket hemat pakaian bayi isi 3 pcs jumper motif lucu unisex. Dibuat dari katun organik combed premium yang sejuk, menyerap keringat, dan memiliki kancing snap bawah bebas nikel untuk memudahkan ganti popok.",
            modelType = Model3DType.PAKAIAN_BAYI,
            isBestSeller = true,
            isMurahEfisien = true,
            colorways = listOf(
                ProductColorway("Boy Blue Marine Set", Color(0xFF0284C7), "Paket Biru Laut", "#0284C7"),
                ProductColorway("Girl Sweet Berry Set", Color(0xFFF472B6), "Paket Berry Pink", "#F472B6"),
                ProductColorway("Neutral Earth Tone", Color(0xFF78716C), "Paket Earth Tone", "#78716C")
            ),
            specifications = listOf(
                ProductSpecification("Isi", "3 Pcs Jumper Romper"),
                ProductSpecification("Bahan", "100% Organic Combed Cotton SNI"),
                ProductSpecification("Kancing", "Nickel-Free Snap Button Anti Karat"),
                ProductSpecification("Ukuran", "0-6 Bulan / 6-12 Bulan")
            ),
            features = listOf(
                "Leher model amplop mudah dipakaikan dari atas maupun bawah",
                "Kancing bawah praktis untuk ganti pampers kilat",
                "Warna sablon ramah lingkungan berbasis air (water-based)"
            )
        ),

        // 12. SETELAN PIYAMA BAYI KANCING DEPAN
        BabyProduct(
            id = "prod_piyama_bayi",
            name = "Setelan Piyama Bayi Lengan Panjang Kancing Depan",
            category = ProductCategory.PAKAIAN,
            price = 38000,
            originalPrice = 55000,
            rating = 4.7f,
            reviewCount = 310,
            soldCount = 1250,
            shortDescription = "Baju tidur bayi atasan kancing depan + celana panjang kain teteron lembut dingin.",
            fullDescription = "Piyama tidur harian bayi yang nyaman untuk tidur malam maupun siang ber-AC. Desain kancing penuh di bagian depan memudahkan ibu memakaikan baju tanpa mengganggu bayi yang sedang tertidur lelap.",
            modelType = Model3DType.PAKAIAN_BAYI,
            isBestSeller = false,
            isMurahEfisien = true,
            colorways = listOf(
                ProductColorway("Mint Dinosaur Dream", Color(0xFF10B981), "Dino Mint", "#10B981"),
                ProductColorway("Pink Little Star", Color(0xFFF472B6), "Bintang Pink", "#F472B6"),
                ProductColorway("Sky Blue Cloud", Color(0xFF38BDF8), "Awan Biru", "#38BDF8")
            ),
            specifications = listOf(
                ProductSpecification("Paket", "1 Baju Lengan Panjang + 1 Celana Panjang"),
                ProductSpecification("Bahan", "Katun TC Soft Premium"),
                ProductSpecification("Karet Celana", "Elastis Lembut Tidak Menekan Perut")
            ),
            features = listOf(
                "Bahan lentur dan menyerap keringat dengan baik",
                "Kancing depan penuh praktis untuk bayi newborn",
                "Jahitan tepi halus tidak gatal di kulit"
            )
        ),

        // 13. KAOS KAKI & SARUNG TANGAN BAYI 3-IN-1
        BabyProduct(
            id = "prod_sarung_tangan",
            name = "Set Sarung Tangan & Kaos Kaki Bayi Anti-Cakar 3 Pasang",
            category = ProductCategory.PAKAIAN,
            price = 25000,
            originalPrice = 36000,
            rating = 4.8f,
            reviewCount = 610,
            soldCount = 2800,
            shortDescription = "Set 3 pasang sarung tangan dan kaos kaki katun halus pelindung cakar kuku bayi.",
            fullDescription = "Paket pelindung jari tangan dan kaki si kecil dengan karet elastis yang pas (tidak terlalu kencang dan tidak mudah melorot). Melindungi wajah bayi dari goresan kuku sendiri dan menjaga tangan tetap hangat.",
            modelType = Model3DType.GENERIC_CUSHION,
            isBestSeller = false,
            isMurahEfisien = true,
            colorways = listOf(
                ProductColorway("Boy Pastel Series", Color(0xFF60A5FA), "Biru & Mint", "#60A5FA"),
                ProductColorway("Girl Pastel Series", Color(0xFFF472B6), "Pink & Peach", "#F472B6")
            ),
            specifications = listOf(
                ProductSpecification("Isi", "3 Pasang Sarung Tangan + 3 Pasang Kaos Kaki"),
                ProductSpecification("Bahan", "100% Katun Ribbed Lembut"),
                ProductSpecification("Karet", "Elastis Gentle Non-Marking (Tidak Membekas)")
            ),
            features = listOf(
                "Karet halus tidak mencekik pergelangan tangan",
                "Mencegah bayi mencakar wajah saat tidur",
                "Bahan adem dan sirkulasi udara baik"
            )
        ),

        // 14. KELAMBU BAYI LIPAT PORTABEL
        BabyProduct(
            id = "prod_kelambu_bayi",
            name = "Kelambu Bayi Lipat Portabel Anti Nyamuk & Serangga",
            category = ProductCategory.AKSESORI,
            price = 54000,
            originalPrice = 75000,
            rating = 4.8f,
            reviewCount = 370,
            soldCount = 1620,
            shortDescription = "Tenda kelambu tidur bayi otomatis pop-up tanpa perlu dirakit, praktis dan aman dari nyamuk.",
            fullDescription = "Melindungi si kecil dari gigitan nyamuk dan serangga berbahaya saat tidur tanpa bahan kimia obat nyamuk. Menggunakan kawat baja elastis yang langsung membuka otomatis saat dikeluarkan dan mudah dilipat kembali menjadi bulat pipih.",
            modelType = Model3DType.GENERIC_CUSHION,
            isBestSeller = false,
            isMurahEfisien = true,
            colorways = listOf(
                ProductColorway("Clean Sky Blue", Color(0xFF38BDF8), "Biru Lembut", "#38BDF8"),
                ProductColorway("Sweet Sakura Pink", Color(0xFFF472B6), "Pink Sakura", "#F472B6")
            ),
            specifications = listOf(
                ProductSpecification("Ukuran Terbuka", "110 x 60 x 55 cm"),
                ProductSpecification("Bahan Jaring", "Polyester Honeycomb Rapat"),
                ProductSpecification("Rangka", "Kawat Baja Galvanis Fleksibel Anti Patah")
            ),
            features = listOf(
                "Sistem pop-up otomatis dalam 1 detik",
                "Kerapatan jaring tinggi nyamuk kecil tidak bisa masuk",
                "Ringan hanya 300 gram, gampang dibawa piknik atau nginap"
            )
        ),

        // 15. MATRAS PERLAK OMPOL WATERPROOF
        BabyProduct(
            id = "prod_perlak_ompol",
            name = "Matras Perlak Ompol Waterproof Katun Organik 70x90",
            category = ProductCategory.AKSESORI,
            price = 35000,
            originalPrice = 50000,
            rating = 4.9f,
            reviewCount = 490,
            soldCount = 2400,
            shortDescription = "Alas perlak anti tembus 3 lapis: permukaan katun adem, tengah busa, bawah TPU waterproof.",
            fullDescription = "Alas tidur anti air berkualitas tinggi yang tidak berisik kresek-kresek saat bayi bergerak. Permukaan atas terbuat dari katun rajut lembut yang ramah kulit, lapisan bawah membran TPU tahan air 100% yang melindungi kasur utama dari ompol dan tumpahan susu.",
            modelType = Model3DType.BEDCOVER_SINGLE,
            isBestSeller = false,
            isMurahEfisien = true,
            colorways = listOf(
                ProductColorway("Blue Animal Zoo", Color(0xFF0284C7), "Kebun Binatang Biru", "#0284C7"),
                ProductColorway("Pink Bunny Dream", Color(0xFFFB7185), "Kelinci Pink", "#FB7185")
            ),
            specifications = listOf(
                ProductSpecification("Ukuran", "70 x 90 cm"),
                ProductSpecification("Struktur 3 Lapis", "Katun Alami + Busa Penyerap + TPU Waterproof"),
                ProductSpecification("Perawatan", "Bisa Dicuci Mesin Cuci")
            ),
            features = listOf(
                "Tidak panas dan tidak gerah untuk bayi",
                "100% kedap air melindungi kasur utama",
                "Jahitan pinggir bisban kain rapi tahan lama"
            )
        ),

        // 16. PAKET HEMAT NEWBORN COMPLETE (SUPER MURAH)
        BabyProduct(
            id = "prod_paket_newborn",
            name = "Paket Hemat Lengkap Newborn Duemar (Super Murah)",
            category = ProductCategory.PAKET_HEMAT,
            price = 298000,
            originalPrice = 420000,
            rating = 5.0f,
            reviewCount = 780,
            soldCount = 890,
            shortDescription = "Bundling terlengkap: Bantal Sofa Bayi + Bantal Set Peyang + Selimut Bedcover + Kacamata Bayi + Handuk Jumbo!",
            fullDescription = "Paket all-in-one paling efisien dan murah untuk menyambut kehadiran buah hati atau kado lahiran spesial! Bunda langsung mendapatkan 5 produk unggulan sekaligus: 1 Bantal Sofa Bayi Biasa, 1 Bantal Set Peyang & 2 Guling, 1 Bedcover Single Lembut, 1 Kacamata Bayi UV Protection, dan 1 Handuk Mandi Microfiber Jumbo. Hemat lebih dari Rp 120.000!",
            modelType = Model3DType.SOFA_DOUBLE,
            isBestSeller = true,
            isMurahEfisien = true,
            colorways = listOf(
                ProductColorway("Pangeran Kecil (Full Blue Theme)", Color(0xFF0284C7), "Tema Biru Pangeran", "#0284C7"),
                ProductColorway("Putri Manis (Full Pink Theme)", Color(0xFFF472B6), "Tema Pink Putri", "#F472B6"),
                ProductColorway("Unisex Sage Sunshine", Color(0xFF0D9488), "Tema Sage Netral", "#0D9488")
            ),
            specifications = listOf(
                ProductSpecification("Isi Paket", "5 Barang Lengkap Pilihan Terbaik"),
                ProductSpecification("Item 1", "Bantal Sofa Bayi Biasa Anti Gumoh"),
                ProductSpecification("Item 2", "Bantal Set Peyang Mahkota + 2 Guling"),
                ProductSpecification("Item 3", "Bedcover Single Lembut 105x85 cm"),
                ProductSpecification("Item 4", "Kacamata Bayi UV Protection Lentur"),
                ProductSpecification("Item 5", "Handuk Bayi Microfiber Jumbo 70x140 cm"),
                ProductSpecification("Bonus Tambahan", "Gratis Greeting Card Kado & Tas Mika Cantik")
            ),
            features = listOf(
                "Solusi hemat terbaik: diskon langsung lebih dari Rp 120.000",
                "Semua barang senada warnanya dan serasi",
                "Dapat dus kado rapi siap langsung diberikan sebagai hadiah kelahiran"
            )
        )
    )

    fun getProductById(id: String): BabyProduct? {
        return products.find { it.id == id }
    }
}
