package com.prashant.material3_compose_template.screens.login

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.prashant.material3_compose_template.MainActivity
import com.prashant.material3_compose_template.R
import com.prashant.material3_compose_template.navigation.LOGIN
import com.prashant.material3_compose_template.uiconfiguration.UIConfiguration


@Composable
fun Login(navHostController: NavHostController, loginVM: LoginVM = hiltViewModel()) {

    val list = arrayListOf(

        "Akaya Kanadaka",
        "Akaya Telivigala",
        "Akronim",
        "Akshar",
        "Aladin",
        "Alata",
        "Alatsi",
        "Albert Sans",
        "Aldrich",
        "Alef",
        "Alegreya",
        "Alegreya SC",
        "Alegreya Sans",
        "Alegreya Sans SC",
        "Aleo",
        "Alex Brush",
        "Alexandria",
        "Alfa Slab One",
        "Alice",
        "Alike",
        "Alike Angular",
        "Alkalami",
        "Allan",
        "Allerta",
        "Allerta Stencil",
        "Allison",
        "Allura",
        "Almarai",
        "Almendra",
        "Almendra Display",
        "Almendra SC",
        "Alumni Sans",
        "Alumni Sans Collegiate One",
        "Alumni Sans Inline One",
        "Alumni Sans Pinstripe",
        "Amarante",
        "Amaranth",
        "Amatic SC",
        "Amethysta",
        "Amiko",
        "Amiri",
        "Amiri Quran",
        "Amita",
        "Anaheim",
        "Andada Pro",
        "Andika",
        "Anek Bangla",
        "Anek Devanagari",
        "Anek Gujarati",
        "Anek Gurmukhi",
        "Anek Kannada",
        "Anek Latin",
        "Anek Malayalam",
        "Anek Odia",
        "Anek Tamil",
        "Anek Telugu",
        "Angkor",
        "Annie Use Your Telescope",
        "Anonymous Pro",
        "Antic",
        "Antic Didone",
        "Antic Slab",
        "Anton",
        "Antonio",
        "Anybody",
        "Arapey",
        "Arbutus",
        "Arbutus Slab",
        "Architects Daughter",
        "Archivo",
        "Archivo Black",
        "Archivo Narrow",
        "Are You Serious",
        "Aref Ruqaa",
        "Aref Ruqaa Ink",
        "Arima",
        "Arima Madurai",
        "Arimo",
        "Arizonia",
    )
    val dark = isSystemInDarkTheme()
    var darkTheme by remember {
        mutableStateOf(dark)
    }
    var fontFamily by remember {
        mutableStateOf("Bentham")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(imageVector = Icons.Default.Lock,
            contentDescription = LOGIN,
            modifier = Modifier.size(80.dp),
            tint = Color.Black.takeIf { !darkTheme } ?: Color.White)

        Text(
            text = "$LOGIN Screen", style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            loginVM.login(navHostController)

        }) {
            Text(
                text = LOGIN, style = MaterialTheme.typography.displayLarge
            )
        }
        MyUI(list) {
            fontFamily = it
        }

        ThemeSwitcher(darkTheme = darkTheme) {
            darkTheme = !darkTheme
        }
        Button(onClick = {
            (MainActivity.weakReference.get() as MainActivity).darkTheme(
                uiConfiguration = UIConfiguration(
                    isDarkTheme = darkTheme, fontFamily = fontFamily, fontStyle = FontStyle.Normal

                )
            )
        }) {
            Text(
                text = "Save", style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun LoginPreview() = Login(rememberNavController())


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyUI(listItems: ArrayList<String>, selectedFontFamily: (String) -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(listItems[0]) }

// We want to react on tap/press on TextField to show menu
    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                // The `menuAnchor` modifier must be passed to the text field for correctness.
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                listItems.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                selectionOption, style = MaterialTheme.typography.bodyLarge.copy(
                                    fontFamily = FontFamily(
                                        Font(
                                            googleFont = GoogleFont(name = selectionOption),
                                            fontProvider = GoogleFont.Provider(
                                                providerAuthority = "com.google.android.gms.fonts",
                                                providerPackage = "com.google.android.gms",
                                                certificates = R.array.com_google_android_gms_fonts_certs
                                            )
                                        )
                                    )
                                )
                            )
                        },
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                            selectedFontFamily(selectedOptionText)
                        },
                        contentPadding = MenuDefaults.DropdownMenuItemContentPadding,
                    )
                }
            }

        }

    }
}

@Composable
fun ThemeSwitcher(
    darkTheme: Boolean = false,
    size: Int = 80,
    iconSize: Dp = (size.dp) / 3,
    padding: Dp = (size * 0.1).dp,
    borderWidth: Dp = 1.dp,
    parentShape: Shape = CircleShape,
    toggleShape: Shape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    onClick: () -> Unit
) {
    val offset by animateDpAsState(
        targetValue = if (darkTheme) 0.dp else size.dp, animationSpec = animationSpec
    )

    Box(modifier = Modifier
        .width((size.dp) * 2)
        .height(size.dp)
        .clip(shape = parentShape)
        .clickable { onClick() }
        .background(MaterialTheme.colorScheme.primary)) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .offset(x = offset)
                .padding(all = padding)
                .clip(shape = toggleShape)
                .background(Color.Cyan)
        ) {}
        Row(
            modifier = Modifier.border(
                border = BorderStroke(
                    width = borderWidth, color = MaterialTheme.colorScheme.primary
                ), shape = parentShape
            )
        ) {
            Box(
                modifier = Modifier.size(size.dp), contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(id = R.drawable.nightlight),
                    contentDescription = "Theme Icon",
                    tint = if (darkTheme) Color.Black
                    else Color.White
                )
            }
            Box(
                modifier = Modifier.size(size.dp), contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(id = R.drawable.light_mode),
                    contentDescription = "Theme Icon",
                    tint = if (!darkTheme) Color.Black
                    else Color.White
                )
            }
        }
    }
}


/**
fun t() {
val dark = ColorScheme(
primary = Color(0.5921569F, 0.8352941f, 0.6f, 1.0F, ColorSpaces.Srgb),
onPrimary = Color(0.0f, 0.22352941F, 0.05490196F, 1.0F, ColorSpaces.Srgb),
primaryContainer = Color(0.08627451F, 0.31764707F, 0.13725491F, 1.0F, ColorSpaces.Srgb),
onPrimaryContainer = Color(0.7019608F, 0.9490196F, 0.7019608F, 1.0F, ColorSpaces.Srgb),
inversePrimary = Color(0.19215687F, 0.41568628F, 0.21960784F, 1.0F, ColorSpaces.Srgb),
secondary = Color(0.72156864F, 0.8F, 0.70980394F, 1.0F, ColorSpaces.Srgb),
onSecondary = Color(0.14117648F, 0.20392157F, 0.14117648F, 1.0F, ColorSpaces.Srgb),
secondaryContainer = Color(0.22745098F, 0.29411766F, 0.22352941F, 1.0F, ColorSpaces.Srgb),
onSecondaryContainer = Color(0.83137256F, 0.9098039F, 0.8156863F, 1.0F, ColorSpaces.Srgb),
tertiary = Color(0.5529412F, 0.81960785F, 0.8627451F, 1.0F, ColorSpaces.Srgb),
onTertiary = Color(0.0F, 0.21176471F, 0.23529412F, 1.0F, ColorSpaces.Srgb),
tertiaryContainer = Color(0.0F, 0.30980393F, 0.34117648F, 1.0F, ColorSpaces.Srgb),
onTertiaryContainer = Color(0.6627451F, 0.93333334F, 0.972549F, 1.0F, ColorSpaces.Srgb),
background = Color(0.101960786F, 0.10980392F, 0.09803922F, 1.0F, ColorSpaces.Srgb),
onBackground = Color(0.8862745F, 0.8901961F, 0.8666667F, 1.0F, ColorSpaces.Srgb),
surface = Color(0.101960786F, 0.10980392F, 0.09803922F, 1.0F, ColorSpaces.Srgb),
onSurface = Color(0.8862745F, 0.8901961F, 0.8666667F, 1.0F, ColorSpaces.Srgb),
surfaceVariant = Color(0.25882354F, 0.28627452F, 0.2509804F, 1.0F, ColorSpaces.Srgb),
onSurfaceVariant = Color(0.75686276F, 0.7882353F, 0.7411765F, 1.0F, ColorSpaces.Srgb),
surfaceTint = Color(0.5921569F, 0.8352941F, 0.6F, 1.0F, ColorSpaces.Srgb),
inverseSurface = Color(0.8862745F, 0.8901961F, 0.8666667F, 1.0F, ColorSpaces.Srgb),
inverseOnSurface = Color(0.18039216F, 0.19215687F, 0.1764706F, 1.0F, ColorSpaces.Srgb),
error = Color(0.9490196F, 0.72156864F, 0.70980394F, 1.0F, ColorSpaces.Srgb),
onError = Color(0.3764706F, 0.078431375F, 0.0627451F, 1.0F, ColorSpaces.Srgb),
errorContainer = Color(0.54901963F, 0.11372549F, 0.09411765F, 1.0F, ColorSpaces.Srgb),
onErrorContainer = Color(0.9764706F, 0.87058824F, 0.8627451F, 1.0F, ColorSpaces.Srgb),
outline = Color(0.54509807F, 0.5764706F, 0.53333336F, 1.0F, ColorSpaces.Srgb),
outlineVariant = Color(0.28627452F, 0.27058825F, 0.30980393F, 1.0F, ColorSpaces.Srgb),
scrim = Color(0.0F, 0.0F, 0.0F, 1.0F, ColorSpaces.Srgb)
)
val light = ColorScheme(
primary = Color(
0.19215687F, 0.41568628F, 0.21960784F, 1.0F, ColorSpaces.Srgb
),
onPrimary = Color(1.0F, 1.0F, 1.0F, 1.0F, ColorSpaces.Srgb),
primaryContainer = Color(0.7019608F, 0.9490196F, 0.7019608F, 1.0F, ColorSpaces.Srgb),
onPrimaryContainer = Color(0.0F, 0.12941177F, 0.023529412F, 1.0F, ColorSpaces.Srgb),
inversePrimary = Color(0.5921569F, 0.8352941F, 0.6F, 1.0F, ColorSpaces.Srgb),
secondary = Color(0.31764707F, 0.3882353F, 0.3137255F, 1.0F, ColorSpaces.Srgb),
onSecondary = Color(1.0F, 1.0F, 1.0F, 1.0F, ColorSpaces.Srgb),
secondaryContainer = Color(0.83137256F, 0.9098039F, 0.8156863F, 1.0F, ColorSpaces.Srgb),
onSecondaryContainer = Color(0.05882353F, 0.12156863F, 0.0627451F, 1.0F, ColorSpaces.Srgb),
tertiary = Color(0.101960786F, 0.40784314F, 0.44313726F, 1.0F, ColorSpaces.Srgb),
onTertiary = Color(1.0F, 1.0F, 1.0F, 1.0F, ColorSpaces.Srgb),
tertiaryContainer = Color(0.6627451F, 0.93333334F, 0.972549F, 1.0F, ColorSpaces.Srgb),
onTertiaryContainer = Color(0.0F, 0.12156863F, 0.13725491F, 1.0F, ColorSpaces.Srgb),
background = Color(0.9882353F, 0.99215686F, 0.9647059F, 1.0F, ColorSpaces.Srgb),
onBackground = Color(0.101960786F, 0.10980392F, 0.09803922F, 1.0F, ColorSpaces.Srgb),
surface = Color(0.9882353F, 0.99215686F, 0.9647059F, 1.0F, ColorSpaces.Srgb),
onSurface = Color(0.101960786F, 0.10980392F, 0.09803922F, 1.0F, ColorSpaces.Srgb),
surfaceVariant = Color(0.87058824F, 0.8980392F, 0.8509804F, 1.0F, ColorSpaces.Srgb),
onSurfaceVariant = Color(0.25882354F, 0.28627452F, 0.2509804F, 1.0F, ColorSpaces.Srgb),
surfaceTint = Color(0.19215687F, 0.41568628F, 0.21960784F, 1.0F, ColorSpaces.Srgb),
inverseSurface = Color(0.18039216F, 0.19215687F, 0.1764706F, 1.0F, ColorSpaces.Srgb),
inverseOnSurface = Color(0.9411765F, 0.94509804F, 0.9254902F, 1.0F, ColorSpaces.Srgb),
error = Color(0.7019608F, 0.14901961F, 0.11764706F, 1.0F, ColorSpaces.Srgb),
onError = Color(1.0F, 1.0F, 1.0F, 1.0F, ColorSpaces.Srgb),
errorContainer = Color(0.9764706f, 0.87058824f, 0.8627451F, 1.0F, ColorSpaces.Srgb),
onErrorContainer = Color(0.25490198F, 0.05490196F, 0.043137256F, 1.0F, ColorSpaces.Srgb),
outline = Color(0.44313726F, 0.47058824F, 0.43529412F, 1.0F, ColorSpaces.Srgb),
outlineVariant = Color(0.7921569F, 0.76862746F, 0.8156863F, 1.0F, ColorSpaces.Srgb),
scrim = Color(0.0F, 0.0F, 0.0F, 1.0F, ColorSpaces.Srgb)
)
}*/
