package com.example.cp.ui.screen.setting.aboutus

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cp.R
import com.example.cp.navigation.ScreensRoute

@Composable
fun AboutUsScreen(
    navController: NavController
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .clickable { navController.popBackStack() }
                    .padding(8.dp)) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "back Icon"
                )
            }

//            Text(text = "لینک های علمی BMI", style = MaterialTheme.typography.titleMedium)


            /*Image(
                modifier = Modifier.clickable {
                    navController.navigate(ScreensRoute.AboutBMI.route)
                },
                painter = painterResource(id = R.drawable.ic_hyperlink),
                contentDescription = "hyperlink Image"
            )*/


        }

        Spacer(modifier = Modifier.height(16.dp))

        SectionTitle("فلج مغزی - دستیار آموزشی و مراقبتی")

        BodyText(
            "این برنامه با هدف افزایش آگاهی، آموزش و کمک به افراد مبتلا به فلج مغزی، خانواده\u200Cها، مراقبین و کادر درمان طراحی شده است. در این برنامه اطلاعات آموزشی معتبر، راهنمای مراقبت، پیگیری وضعیت و سایر امکانات مرتبط در اختیار کاربران قرار می\u200Cگیرد.\n" +
                    "\n" +
                    "تمامی مطالب آموزشی این برنامه بر اساس منابع علمی معتبر و راهنماهای بالینی تهیه شده\u200Cاند و تلاش شده است اطلاعاتی دقیق، به\u200Cروز و قابل اعتماد ارائه شود."
        )

        Spacer(Modifier.height(24.dp))

        SectionTitle("⚠️ سلب مسئولیت")

        BodyText(
            """
                این برنامه صرفاً یک ابزار آموزشی و کمکی است و جایگزین پزشک، فیزیوتراپیست، کاردرمانگر، گفتاردرمانگر یا سایر متخصصان حوزه سلامت نیست.

                اطلاعات ارائه‌شده در این برنامه تنها جهت افزایش آگاهی کاربران بوده و نباید مبنای تشخیص، درمان یا تصمیم‌گیری پزشکی قرار گیرد.

                هرگونه تصمیم درباره ارزیابی، درمان، مصرف دارو، برنامه توانبخشی یا تغییر در روند درمان باید تنها با نظر پزشک یا تیم درمان انجام شود.

                در صورت بروز هرگونه وضعیت اورژانسی یا نگرانی درباره وضعیت جسمانی فرد، فوراً به مراکز درمانی مراجعه کنید.

                توسعه‌دهندگان این برنامه مسئولیتی در قبال تصمیمات درمانی کاربران بر اساس اطلاعات موجود در برنامه بر عهده ندارند.
                """.trimIndent()
        )

        Spacer(Modifier.height(24.dp))

        SectionTitle("🔒 سیاست حفظ حریم خصوصی")

        BodyText(
            """حفظ حریم خصوصی کاربران برای ما اهمیت ویژه‌ای دارد.

این برنامه به گونه‌ای طراحی شده است که اطلاعات کاربران فقط روی دستگاه خود کاربر ذخیره شود.

اطلاعاتی مانند یادداشت‌ها، تنظیمات برنامه، سوابق ثبت‌شده و سایر اطلاعات واردشده توسط کاربر تنها برای ارائه امکانات برنامه استفاده می‌شوند.

تمام اطلاعات ثبت‌شده محفوظ بوده و:

• به هیچ شخص حقیقی یا حقوقی فروخته نمی‌شود.
• در اختیار هیچ شرکت، سازمان یا شخص ثالث قرار نمی‌گیرد.
• برای اهداف تبلیغاتی یا بازاریابی استفاده نمی‌شود.
• بدون رضایت کاربر منتشر یا منتقل نخواهد شد. """.trimIndent()
        )

        Spacer(Modifier.height(24.dp))

        SectionTitle("📚 منابع")

        val refs = listOf(
            "American Academy for Cerebral Palsy and Developmental Medicine (AACPDM)",
            "World Health Organization (WHO)",
            "Centers for Disease Control and Prevention (CDC)",
            "National Institute of Neurological Disorders and Stroke (NINDS)",
            "UpToDate",
            "Nelson Textbook of Pediatrics",
            "Campbell's Physical Therapy for Children",
            "CanChild Centre for Childhood Disability Research",
            "American Academy of Pediatrics (AAP)"

        )

        refs.forEach {
            Text(
                text = "• $it",
                style = MaterialTheme.typography.bodyMedium.copy(textDirection = TextDirection.Ltr),
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
            )
        }

        Spacer(Modifier.height(40.dp))
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
    )
}

@Composable
fun BodyText(text: String) {
    Text(modifier = Modifier.fillMaxWidth(),
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Justify,
        lineHeight = 28.sp
    )
}