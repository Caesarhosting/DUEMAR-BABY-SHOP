package com.example.model

data class BabySizeGuideItem(
    val code: String,
    val label: String,
    val ageRange: String,
    val weightRange: String,
    val heightRange: String,
    val chestCircumference: String,
    val bodyLength: String,
    val description: String
)

object BabySizeCatalog {
    val standardSizes: List<BabySizeGuideItem> = listOf(
        BabySizeGuideItem(
            code = "NB",
            label = "Newborn (0 - 3 Bulan)",
            ageRange = "0 - 3 Bulan",
            weightRange = "2.5 - 5.0 kg",
            heightRange = "48 - 58 cm",
            chestCircumference = "40 - 43 cm",
            bodyLength = "35 - 37 cm",
            description = "Sangat pas untuk bayi baru lahir, potongan leher amplop lembut tidak menjepit leher."
        ),
        BabySizeGuideItem(
            code = "S",
            label = "3 - 6 Bulan (S)",
            ageRange = "3 - 6 Bulan",
            weightRange = "5.0 - 7.5 kg",
            heightRange = "58 - 66 cm",
            chestCircumference = "44 - 47 cm",
            bodyLength = "38 - 41 cm",
            description = "Ukuran paling populer untuk bayi mulai aktif tengkurap & berguling, ada ruang ekstra untuk popok."
        ),
        BabySizeGuideItem(
            code = "M",
            label = "6 - 12 Bulan (M)",
            ageRange = "6 - 12 Bulan",
            weightRange = "7.5 - 10.0 kg",
            heightRange = "66 - 76 cm",
            chestCircumference = "48 - 51 cm",
            bodyLength = "42 - 45 cm",
            description = "Nyaman untuk fase merangkak dan belajar berdiri, jahitan selangkangan elastis anti robek."
        ),
        BabySizeGuideItem(
            code = "L",
            label = "12 - 18 Bulan (L)",
            ageRange = "12 - 18 Bulan",
            weightRange = "10.0 - 12.5 kg",
            heightRange = "76 - 85 cm",
            chestCircumference = "52 - 55 cm",
            bodyLength = "46 - 49 cm",
            description = "Bahan leluasa untuk anak yang sudah aktif berjalan dan bermain seharian."
        ),
        BabySizeGuideItem(
            code = "XL",
            label = "18 - 24 Bulan (XL)",
            ageRange = "18 - 24 Bulan",
            weightRange = "12.5 - 15.0 kg",
            heightRange = "85 - 95 cm",
            chestCircumference = "56 - 59 cm",
            bodyLength = "50 - 53 cm",
            description = "Ukuran toddler dengan lingkar perut elastis dan kenyamanan maksimal."
        )
    )

    fun recommendSize(weightKg: Float, ageMonths: Int): BabySizeGuideItem {
        return when {
            weightKg <= 5.0f || ageMonths <= 2 -> standardSizes[0] // NB
            weightKg <= 7.5f || ageMonths <= 5 -> standardSizes[1] // S (3-6m)
            weightKg <= 10.0f || ageMonths <= 11 -> standardSizes[2] // M (6-12m)
            weightKg <= 12.5f || ageMonths <= 17 -> standardSizes[3] // L (12-18m)
            else -> standardSizes[4] // XL (18-24m)
        }
    }
}
