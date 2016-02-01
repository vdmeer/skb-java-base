/* Copyright 2015 Sven van der Meer <vdmeer.sven@mykolab.com\>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.vandermeer.skb.base.encodings;

import org.apache.commons.lang3.StringUtils;

/**
 * Dictionary to translate HTML character encodings to LaTeX character encodings.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.5 build 150910 (10-Sep-15) for Java 1.8
 */
public class Text2Html implements Translator {

	/** Array of searchable strings. */
	protected String[] searchList = new String[]{
		"#", "%", "&", "<", ">", "?", "\"", "\\", "¡", "¢", "£", "¤", "¥", "¦", "§", "¨", "©", "ª", "«", "¬", "­", "®", "¯", "°", "±", "²", "³", "´", "µ", "¶", "·", "¸", "¹", "º", "»", "¼", "½", "¾", "¿", "À", "Á", "Â", "Ã", "Ä", "Å", "Æ", "Ç", "È", "É", "Ê", "Ë", "Ì", "Í", "Î", "Ï", "Ð", "Ñ", "Ò", "Ó", "Ô", "Õ", "Ö", "×", "Ø", "Ù", "Ú", "Û", "Ü", "Ý", "Þ", "ß", "à", "á", "â", "ã", "ä", "å", "æ", "ç", "è", "é", "ê", "ë", "ì", "í", "î", "ï", "ð", "ñ", "ò", "ó", "ô", "õ", "ö", "÷", "ø", "ù", "ú", "û", "ü", "ý", "þ", "ÿ", "Ā", "ā", "Ă", "ă", "Ą", "ą", "Ć", "ć", "Ĉ", "ĉ", "Ċ", "ċ", "Č", "č", "Ď", "ď", "Đ", "đ", "Ē", "ē", "Ĕ", "ĕ", "Ė", "ė", "Ę", "ę", "Ě", "ě", "Ĝ", "ĝ", "Ğ", "ğ", "Ġ", "ġ", "Ģ", "ģ", "Ĥ", "ĥ", "Ħ", "ħ", "Ĩ", "ĩ", "Ī", "ī", "Ĭ", "ĭ", "Į", "į", "İ", "ı", "Ĳ", "ĳ", "Ĵ", "ĵ", "Ķ", "ķ", "ĸ", "Ĺ", "ĺ", "Ļ", "ļ", "Ľ", "ľ", "Ŀ", "ŀ", "Ł", "ł", "Ń", "ń", "Ņ", "ņ", "Ň", "ň", "ŉ", "Ŋ", "ŋ", "Ō", "ō", "Ŏ", "ŏ", "Ő", "ő", "Œ", "œ", "Ŕ", "ŕ", "Ŗ", "ŗ", "Ř", "ř", "Ś", "ś", "Ŝ", "ŝ", "Ş", "ş", "Ţ", "ţ", "Ť", "ť", "Ŧ", "ŧ", "Ũ", "ũ", "Ū", "ū", "Ŭ", "ŭ", "Ů", "ů", "Ű", "ű", "Ų", "ų", "Ŵ", "ŵ", "Ŷ", "ŷ", "Ÿ", "Ź", "ź", "Ż", "ż", "Ž", "ž", "ſ", "ƀ", "Ɓ", "Ƃ", "ƃ", "Ƅ", "ƅ", "Ɔ", "Ƈ", "ƈ", "Ɖ", "Ɗ", "Ƌ", "ƌ", "ƍ", "Ǝ", "Ə", "Ɛ", "Ƒ", "ƒ", "Ɠ", "Ɣ", "ƕ", "Ɩ", "Ɨ", "Ƙ", "ƙ", "ƚ", "ƛ", "Ɯ", "Ɲ", "ƞ", "Ɵ", "Ơ", "ơ", "Ƣ", "ƣ", "Ƥ", "ƥ", "Ʀ", "Ƨ", "ƨ", "Ʃ", "ƪ", "ƫ", "Ƭ", "ƭ", "Ʈ", "Ư", "ư", "Ʊ", "Ʋ", "Ƴ", "ƴ", "Ƶ", "ƶ", "Ʒ", "Ƹ", "ƹ", "ƺ", "ƻ", "Ƽ", "ƽ", "ƾ", "ƿ", "ǀ", "ǁ", "ǂ", "ǃ", "Ǆ", "ǅ", "ǆ", "Ǉ", "ǈ", "ǉ", "Ǌ", "ǋ", "ǌ", "Ǎ", "ǎ", "Ǐ", "ǐ", "Ǒ", "ǒ", "Ǔ", "ǔ", "Ǖ", "ǖ", "Ǘ", "ǘ", "Ǚ", "ǚ", "Ǜ", "ǜ", "ǝ", "Ǟ", "ǟ", "Ǡ", "ǡ", "Ǣ", "ǣ", "Ǥ", "ǥ", "Ǧ", "ǧ", "Ǩ", "ǩ", "Ǫ", "ǫ", "Ǭ", "ǭ", "Ǯ", "ǯ", "ǰ", "Ǳ", "ǲ", "ǳ", "Ǵ", "ǵ", "Ƕ", "Ƿ", "Ǹ", "ǹ", "Ǻ", "ǻ", "Ǽ", "ǽ", "Ǿ", "ǿ", "Ȁ", "ȁ", "Ȃ", "ȃ", "Ȅ", "ȅ", "Ȇ", "ȇ", "Ȉ", "ȉ", "Ȋ", "ȋ", "Ȍ", "ȍ", "Ȏ", "ȏ", "Ȑ", "ȑ", "Ȓ", "ȓ", "Ȕ", "ȕ", "Ȗ", "ȗ", "Ș", "ș", "Ț", "ț", "Ȝ", "ȝ", "Ȟ", "ȟ", "Ƞ", "ȡ", "Ȣ", "ȣ", "Ȥ", "ȥ", "Ȧ", "ȧ", "Ȩ", "ȩ", "Ȫ", "ȫ", "Ȭ", "ȭ", "Ȯ", "ȯ", "Ȱ", "ȱ", "Ȳ", "ȳ", "ȴ", "ȵ", "ȶ", "ȷ", "ȸ", "ȹ", "Ⱥ", "Ȼ", "ȼ", "Ƚ", "Ⱦ", "ȿ", "ɀ", "Ɂ", "ɂ", "Ƀ", "Ʉ", "Ʌ", "Ɇ", "ɇ", "Ɉ", "ɉ", "Ɋ", "ɋ", "Ɍ", "ɍ", "Ɏ", "ɏ", "ʰ", "ʱ", "ʲ", "ʳ", "ʴ", "ʵ", "ʶ", "ʷ", "ʸ", "ʹ", "ʺ", "ʻ", "ʼ", "ʽ", "ʾ", "ʿ", "ˀ", "ˁ", "˂", "˃", "˄", "˅", "ˆ", "ˇ", "ˈ", "ˉ", "ˊ", "ˋ", "ˌ", "ˍ", "ˎ", "ˏ", "ː", "ˑ", "˒", "˓", "˔", "˕", "˖", "˗", "˘", "˙", "˚", "˛", "˜", "˝", "˞", "˟", "ˠ", "ˡ", "ˢ", "ˣ", "ˤ", "˥", "˦", "˧", "˨", "˩", "˪", "˫", "ˬ", "˭", "ˮ", "˯", "˰", "˱", "˲", "˳", "˴", "˵", "˶", "˷", "˸", "˹", "˺", "˻", "˼", "˽", "˾", "˿", "̂", "̄", "̅", "̆", "̇", "̈", "̊", "̋", "̌", "̍", "̎", "̏", "̐", "̑", "̒", "̓", "̔", "̕", "̖", "̗", "̘", "̙", "̚", "̛", "̜", "̝", "̞", "̟", "̠", "̡", "̢", "̤", "̥", "̦", "̧", "̨", "̩", "̪", "̫", "̬", "̭", "̮", "̯", "̰", "̱", "̲", "̳", "̴", "̵", "̶", "̷", "̸", "̹", "̺", "̻", "̼", "̽", "̾", "̿", "̀", "́", "͂", "̓", "̈́", "ͅ", "͆", "͇", "͈", "͉", "͊", "͋", "͌", "͍", "͎", "͏", "͐", "͑", "͒", "͓", "͔", "͕", "͖", "͗", "͘", "͙", "͚", "͛", "͜", "͝", "͞", "͟", "͠", "͡", "͢", "ͣ", "ͤ", "ͥ", "ͦ", "ͧ", "ͨ", "ͩ", "ͪ", "ͫ", "ͬ", "ͭ", "ͮ", "ͯ", "Ͱ", "ͱ", "Ͳ", "ͳ", "ʹ", "͵", "Ͷ", "ͷ", "ͺ", "ͻ", "ͼ", "ͽ", ";", "Ϳ", "΄", "΅", "Ά", "·", "Έ", "Ή", "Ί", "Ό", "Ύ", "Ώ", "ΐ", "Α", "Β", "Γ", "Δ", "Ε", "Ζ", "Η", "Θ", "Ι", "Κ", "Λ", "Μ", "Ν", "Ξ", "Ο", "Π", "Ρ", "Σ", "Τ", "Υ", "Φ", "Χ", "Ψ", "Ω", "Ϊ", "Ϋ", "ά", "έ", "ή", "ί", "ΰ", "α", "β", "γ", "δ", "ε", "ζ", "η", "θ", "ι", "κ", "λ", "μ", "ν", "ξ", "ο", "π", "ρ", "ς", "σ", "τ", "υ", "φ", "χ", "ψ", "ω", "ϊ", "ϋ", "ό", "ύ", "ώ", "Ϗ", "ϐ", "ϑ", "ϒ", "ϓ", "ϔ", "ϕ", "ϖ", "ϗ", "Ϙ", "ϙ", "Ϛ", "ϛ", "Ϝ", "ϝ", "Ϟ", "ϟ", "Ϡ", "ϡ", "Ϣ", "ϣ", "Ϥ", "ϥ", "Ϧ", "ϧ", "Ϩ", "ϩ", "Ϫ", "ϫ", "Ϭ", "ϭ", "Ϯ", "ϯ", "ϰ", "ϱ", "ϲ", "ϳ", "ϴ", "ϵ", "϶", "Ϸ", "ϸ", "Ϲ", "Ϻ", "ϻ", "ϼ", "Ͻ", "Ͼ", "Ͽ"
	};

	/** Array of replacements for searchable strings. */
	protected String[] replacementList = new String[]{
		"&#803;", "&#37;", "&amp;", "&lt;", "&gt;", "&#63;", "&quot;", "&#92;", "&iexcl;", "&cent;", "&pound;", "&curren;", "&yen;", "&brvbar;", "&sect;", "&uml;", "&copy;", "&ordf;", "&laquo;", "&not;", "&shy;", "&reg;", "&macr;", "&deg;", "&plusmn;", "&sup2;", "&sup3;", "&acute;", "&micro;", "&para;", "&middot;", "&cedil;", "&sup1;", "&ordm;", "&raquo;", "&frac14;", "&frac12;", "&frac34;", "&iquest;", "&Agrave;", "&Aacute;", "&Acirc;", "&Atilde;", "&Auml;", "&Aring;", "&AElig;", "&Ccedil;", "&Egrave;", "&Eacute;", "&Ecirc;", "&Euml;", "&Igrave;", "&Iacute;", "&Icirc;", "&Iuml;", "&ETH;", "&Ntilde;", "&Ograve;", "&Oacute;", "&Ocirc;", "&Otilde;", "&Ouml;", "&times;", "&Oslash;", "&Ugrave;", "&Uacute;", "&Ucirc;", "&Uuml;", "&Yacute;", "&THORN;", "&szlig;", "&agrave;", "&aacute;", "&acirc;", "&atilde;", "&auml;", "&aring;", "&aelig;", "&ccedil;", "&egrave;", "&eacute;", "&ecirc;", "&euml;", "&igrave;", "&iacute;", "&icirc;", "&iuml;", "&eth;", "&ntilde;", "&ograve;", "&oacute;", "&ocirc;", "&otilde;", "&ouml;", "&divide;", "&oslash", "&ugrave", "&uacute", "&ucirc;", "&uuml;", "&yacute;", "&thorn;", "&yuml;", "&#256;", "&#257;", "&#258;", "&#259;", "&#260;", "&#261;", "&#262;", "&#263;", "&#264;", "&#265;", "&#266;", "&#267;", "&#268;", "&#269;", "&#270;", "&#271;", "&#272;", "&#273;", "&#274;", "&#275;", "&#276;", "&#277;", "&#278;", "&#279;", "&#280;", "&#281;", "&#282;", "&#283;", "&#284;", "&#285;", "&#286;", "&#287;", "&#288;", "&#289;", "&#290;", "&#291;", "&#292;", "&#293;", "&#294;", "&#295;", "&#296;", "&#297;", "&#298;", "&#299;", "&#300;", "&#301;", "&#302;", "&#303;", "&#304;", "&#305;", "&#306;", "&#307;", "&#308;", "&#309;", "&#310;", "&#311;", "&#312;", "&#313;", "&#314;", "&#315;", "&#316;", "&#317;", "&#318;", "&#319;", "&#320;", "&#321;", "&#322;", "&#323;", "&#324;", "&#325;", "&#326;", "&#327;", "&#328;", "&#329;", "&#330;", "&#331;", "&#332;", "&#333;", "&#334;", "&#335;", "&#336;", "&#337;", "&OElig;", "&oelig;", "&#340;", "&#341;", "&#342;", "&#343;", "&#344;", "&#345;", "&#346;", "&#347;", "&#348;", "&#349;", "&#350;", "&#351;", "&#354;", "&#355;", "&#356;", "&#357;", "&#358;", "&#359;", "&#360;", "&#361;", "&#362;", "&#363;", "&#364;", "&#365;", "&#366;", "&#367;", "&#368;", "&#369;", "&#370;", "&#371;", "&#372;", "&#373;", "&#374;", "&#375;", "&Yuml;", "&#377;", "&#378;", "&#379;", "&#380;", "&#381;", "&#382;", "&#383;", "&#384;", "&#385;", "&#386;", "&#387;", "&#388;", "&#389;", "&#390;", "&#391;", "&#392;", "&#393;", "&#394;", "&#395;", "&#396;", "&#397;", "&#398;", "&#399;", "&#400;", "&#401;", "&#402;", "&#403;", "&#404;", "&#405;", "&#406;", "&#407;", "&#408;", "&#409;", "&#410;", "&#411;", "&#412;", "&#413;", "&#414;", "&#415;", "&#416;", "&#417;", "&#418;", "&#419;", "&#420;", "&#421;", "&#422;", "&#423;", "&#424;", "&#425;", "&#426;", "&#427;", "&#428;", "&#429;", "&#430;", "&#431;", "&#432;", "&#433;", "&#434;", "&#435;", "&#436;", "&#437;", "&#438;", "&#439;", "&#440;", "&#441;", "&#442;", "&#443;", "&#444;", "&#445;", "&#446;", "&#447;", "&#448;", "&#449;", "&#450;", "&#451;", "&#452;", "&#453;", "&#454;", "&#455;", "&#456;", "&#457;", "&#458;", "&#459;", "&#460;", "&#461;", "&#462;", "&#463;", "&#464;", "&#465;", "&#466;", "&#467;", "&#468;", "&#469;", "&#470;", "&#471;", "&#472;", "&#473;", "&#474;", "&#475;", "&#476;", "&#477;", "&#478;", "&#479;", "&#480;", "&#481;", "&#482;", "&#483;", "&#484;", "&#485;", "&#486;", "&#487;", "&#488;", "&#489;", "&#490;", "&#491;", "&#492;", "&#493;", "&#494;", "&#495;", "&#496;", "&#497;", "&#498;", "&#499;", "&#500;", "&#501;", "&#502;", "&#503;", "&#504;", "&#505;", "&#506;", "&#507;", "&#508;", "&#509;", "&#510;", "&#511;", "&#512;", "&#513;", "&#514;", "&#515;", "&#516;", "&#517;", "&#518;", "&#519;", "&#520;", "&#521;", "&#522;", "&#523;", "&#524;", "&#525;", "&#526;", "&#527;", "&#528;", "&#529;", "&#530;", "&#531;", "&#532;", "&#533;", "&#534;", "&#535;", "&#536;", "&#537;", "&#538;", "&#539;", "&#540;", "&#541;", "&#542;", "&#543;", "&#544;", "&#545;", "&#546;", "&#547;", "&#548;", "&#549;", "&#550;", "&#551;", "&#552;", "&#553;", "&#554;", "&#555;", "&#556;", "&#557;", "&#558;", "&#559;", "&#560;", "&#561;", "&#562;", "&#563;", "&#564;", "&#565;", "&#566;", "&#567;", "&#568;", "&#569;", "&#570;", "&#571;", "&#572;", "&#573;", "&#574;", "&#575;", "&#576;", "&#577;", "&#578;", "&#579;", "&#580;", "&#581;", "&#582;", "&#583;", "&#584;", "&#585;", "&#586;", "&#587;", "&#588;", "&#589;", "&#590;", "&#591;", "&#688;", "&#689;", "&#690;", "&#691;", "&#692;", "&#693;", "&#694;", "&#695;", "&#696;", "&#697;", "&#698;", "&#699;", "&#700;", "&#701;", "&#702;", "&#703;", "&#704;", "&#705;", "&#706;", "&#707;", "&#708;", "&#709;", "&#710;", "&#711;", "&#712;", "&#713;", "&#714;", "&#715;", "&#716;", "&#717;", "&#718;", "&#719;", "&#720;", "&#721;", "&#722;", "&#723;", "&#724;", "&#725;", "&#726;", "&#727;", "&#728;", "&#729;", "&#730;", "&#731;", "&#732;", "&#733;", "&#734;", "&#735;", "&#736;", "&#737;", "&#738;", "&#739;", "&#740;", "&#741;", "&#742;", "&#743;", "&#744;", "&#745;", "&#746;", "&#747;", "&#748;", "&#749;", "&#750;", "&#751;", "&#752;", "&#753;", "&#754;", "&#755;", "&#756;", "&#757;", "&#758;", "&#759;", "&#760;", "&#761;", "&#762;", "&#763;", "&#764;", "&#765;", "&#766;", "&#767;", "&#770;", "&#772;", "&#773;", "&#774;", "&#775;", "&#776;", "&#778;", "&#779;", "&#780;", "&#781;", "&#782;", "&#783;", "&#784;", "&#785;", "&#786;", "&#787;", "&#788;", "&#789;", "&#790;", "&#791;", "&#792;", "&#793;", "&#794;", "&#795;", "&#796;", "&#797;", "&#798;", "&#799;", "&#800;", "&#801;", "&#802;", "&#804;", "&#805;", "&#806;", "&#807;", "&#808;", "&#809;", "&#810;", "&#811;", "&#812;", "&#813;", "&#814;", "&#815;", "&#816;", "&#817;", "&#818;", "&#819;", "&#820;", "&#821;", "&#822;", "&#823;", "&#824;", "&#825;", "&#826;", "&#827;", "&#828;", "&#829;", "&#830;", "&#831;", "&#832;", "&#833;", "&#834;", "&#835;", "&#836;", "&#837;", "&#838;", "&#839;", "&#840;", "&#841;", "&#842;", "&#843;", "&#844;", "&#845;", "&#846;", "&#847;", "&#848;", "&#849;", "&#850;", "&#851;", "&#852;", "&#853;", "&#854;", "&#855;", "&#856;", "&#857;", "&#858;", "&#859;", "&#860;", "&#861;", "&#862;", "&#863;", "&#864;", "&#865;", "&#866;", "&#867;", "&#868;", "&#869;", "&#870;", "&#871;", "&#872;", "&#873;", "&#874;", "&#875;", "&#876;", "&#877;", "&#878;", "&#879;", "&#880;", "&#881;", "&#882;", "&#883;", "&#884;", "&#885;", "&#886;", "&#887;", "&#890;", "&#891;", "&#892;", "&#893;", "&#894;", "&#895;", "&#900;", "&#901;", "&#902;", "&#903;", "&#904;", "&#905;", "&#906;", "&#908;", "&#910;", "&#911;", "&#912;", "&Alpha;", "Beta;", "&Gamma;", "&Delta;", "&Epsilon;", "&Zeta;", "&Eta;", "&Theta;", "&Iota;", "&Kappa;", "&Lambda;", "&Mu;", "&Nu;", "&Xi;", "&Omicron;", "&Pi;", "&Rho;", "&Sigma;", "&Tau;", "&Upsilon;", "&Phi;", "&Chi;", "&Psi;", "&Omega;", "&#938;", "&#939;", "&#940;", "&#941;", "&#942;", "&#943;", "&#944;", "&alpha;", "&beta;", "&gamma;", "&delta;", "&epsilon;", "&zeta;", "&eta;", "&theta;", "&iota;", "&kappa;", "&lambda;", "&mu;", "&nu;", "&xi;", "&omicron;", "&pi;", "&rho;", "&sigmaf;", "&sigma;", "&tau;", "&upsilon;", "&phi;", "&chi;", "&psi;", "&omega;", "&#970;", "&#971;", "&#972;", "&#973;", "&#974;", "&#975;", "&#976;", "&thetasym;", "&upsih;", "&#979;", "&#980;", "&#981;", "&piv;", "&#983;", "&#984;", "&#985;", "&#986;", "&#987;", "&#988;", "&#989;", "&#990;", "&#991;", "&#992;", "&#993;", "&#994;", "&#995;", "&#996;", "&#997;", "&#998;", "&#999;", "&#1000;", "&#1001;", "&#1002;", "&#1003;", "&#1004;", "&#1005;", "&#1006;", "&#1007;", "&#1008;", "&#1009;", "&#1010;", "&#1011;", "&#1012;", "&#1013;", "&#1014;", "&#1015;", "&#1016;", "&#1017;", "&#1018;", "&#1019;", "&#1020;", "&#1021;", "&#1022;", "&#1023;"
	};

	/** Array of searchable HTML entity strings. */
	protected String[] searchListHE = new String[]{
		"</b>", "</i>", "</li>", "</ol>", "</sub>", "</sup>", "</ul>", "<b>", "<br />", "<br/>", "<br>", "<i>", "<li>", "<ol>", "<sub>", "<sup>", "<ul>"
	};

	/** Array of replacements for searchable HTML entity strings. */
	protected String[] replacementListHE = new String[]{
		"(((/b)))", "(((/i)))", "(((/li)))", "(((/ol)))", "(((/sub)))", "(((/sup)))", "(((/ul)))", "(((b)))", "(((br /)))", "(((br/)))", "(((br)))", "(((i)))", "(((li)))", "(((ol)))", "(((sub)))", "(((sup)))", "(((ul)))"
	};

	@Override
	public String translate(String input){
		String ret = StringUtils.replaceEach(input, this.searchListHE, this.replacementListHE);
		ret = StringUtils.replaceEach(ret, this.searchList, this.replacementList);
		ret = StringUtils.replaceEach(ret, this.replacementListHE, this.searchListHE);
		return ret;
	}
}
