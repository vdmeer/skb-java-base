/* Copyright 2014 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.skb.base.utils;

import java.util.Random;

//{@linkplain http://www.jave.de/figlet/fonts/overview.html}
/**
 * Collection of FigLets for funny shutdown messages.
 * FigLets are special fonts.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
 * @since      v0.1.4 (was in ExecS before)
 */
public enum FigletShutdown {
	Banner3_D	(new String[]{":'######::'##::::'##:'##::::'##:'########:'########:::'#######::'##:::::'##:'##::: ##:","'##... ##: ##:::: ##: ##:::: ##:... ##..:: ##.... ##:'##.... ##: ##:'##: ##: ###:: ##:"," ##:::..:: ##:::: ##: ##:::: ##:::: ##:::: ##:::: ##: ##:::: ##: ##: ##: ##: ####: ##:",". ######:: #########: ##:::: ##:::: ##:::: ##:::: ##: ##:::: ##: ##: ##: ##: ## ## ##:",":..... ##: ##.... ##: ##:::: ##:::: ##:::: ##:::: ##: ##:::: ##: ##: ##: ##: ##. ####:","'##::: ##: ##:::: ##: ##:::: ##:::: ##:::: ##:::: ##: ##:::: ##: ##: ##: ##: ##:. ###:",". ######:: ##:::: ##:. #######::::: ##:::: ########::. #######::. ###. ###:: ##::. ##:",":......:::..:::::..:::.......::::::..:::::........::::.......::::...::...:::..::::..::"}),
	Banner4		(new String[]{"..######..##.....##.##.....##.########.########...#######..##......##.##....##",".##....##.##.....##.##.....##....##....##.....##.##.....##.##..##..##.###...##",".##.......##.....##.##.....##....##....##.....##.##.....##.##..##..##.####..##","..######..#########.##.....##....##....##.....##.##.....##.##..##..##.##.##.##",".......##.##.....##.##.....##....##....##.....##.##.....##.##..##..##.##..####",".##....##.##.....##.##.....##....##....##.....##.##.....##.##..##..##.##...###","..######..##.....##..#######.....##....########...#######...###..###..##....##"}),
	Basic		(new String[]{".d8888. db   db db    db d888888b d8888b.  .d88b.  db   d8b   db d8b   db","88'  YP 88   88 88    88 `~~88~~' 88  `8D .8P  Y8. 88   I8I   88 888o  88","`8bo.   88ooo88 88    88    88    88   88 88    88 88   I8I   88 88V8o 88","  `Y8b. 88~~~88 88    88    88    88   88 88    88 Y8   I8I   88 88 V8o88","db   8D 88   88 88b  d88    88    88  .8D `8b  d8' `8b d8'8b d8' 88  V888","`8888Y' YP   YP ~Y8888P'    YP    Y8888D'  `Y88P'   `8b8' `8d8'  VP   V8P"}),
	Binary		(new String[]{"01110011 01101000 01110101 01110100 01100100 01101111 01110111 01101110"}),
	Bright		(new String[]{"..####..##..##.##..##.######.#####...####..##...##.##..##.",".##.....##..##.##..##...##...##..##.##..##.##...##.###.##.","..####..######.##..##...##...##..##.##..##.##.#.##.##.###.",".....##.##..##.##..##...##...##..##.##..##.#######.##..##.","..####..##..##..####....##...#####...####...##.##..##..##.",".........................................................."}),
	Doom		(new String[]{"     _           _      _","    | |         | |    | |"," ___| |__  _   _| |_ __| | _____      ___ __","/ __| '_ \\| | | | __/ _` |/ _ \\ \\ /\\ / / '_ \\","\\__ \\ | | | |_| | || (_| | (_) \\ V  V /| | | |","|___/_| |_|\\__,_|\\__\\__,_|\\___/ \\_/\\_/ |_| |_|"}),
	Epic		(new String[]{" _______                   _________ ______   _______           _","(  ____ \\|\\     /||\\     /|\\__   __/(  __  \\ (  ___  )|\\     /|( (    /|","| (    \\/| )   ( || )   ( |   ) (   | (  \\  )| (   ) || )   ( ||  \\  ( |","| (_____ | (___) || |   | |   | |   | |   ) || |   | || | _ | ||   \\ | |","(_____  )|  ___  || |   | |   | |   | |   | || |   | || |( )| || (\\ \\) |","      ) || (   ) || |   | |   | |   | |   ) || |   | || || || || | \\   |","/\\____) || )   ( || (___) |   | |   | (__/  )| (___) || () () || )  \\  |","\\_______)|/     \\|(_______)   )_(   (______/ (_______)(_______)|/    )_)"}),
	Hex			(new String[]{"73 68 75 74 64 6F 77 6E"}),
	Larry3D		(new String[]{"       __               __       __","      /\\ \\             /\\ \\__   /\\ \\","  ____\\ \\ \\___   __  __\\ \\ ,_\\  \\_\\ \\    ___   __  __  __    ___"," /',__\\\\ \\  _ `\\/\\ \\/\\ \\\\ \\ \\/  /'_` \\  / __`\\/\\ \\/\\ \\/\\ \\ /' _ `\\","/\\__, `\\\\ \\ \\ \\ \\ \\ \\_\\ \\\\ \\ \\_/\\ \\L\\ \\/\\ \\L\\ \\ \\ \\_/ \\_/ \\/\\ \\/\\ \\","\\/\\____/ \\ \\_\\ \\_\\ \\____/ \\ \\__\\ \\___,_\\ \\____/\\ \\___x___/'\\ \\_\\ \\_\\"," \\/___/   \\/_/\\/_/\\/___/   \\/__/\\/__,_ /\\/___/  \\/__//__/   \\/_/\\/_/"}),
	LilDevil	(new String[]{" (`-').->(`-').->           (`-')     _(`-')                  .->   <-. (`-')_"," ( OO)_  (OO )__      .->   ( OO).-> ( (OO ).->    .->    (`(`-')/`)   \\( OO) )","(_)--\\_),--. ,'-',--.(,--.  /    '._  \\    .'_(`-')----. ,-`( OO).',,--./ ,--/","/    _ /|  | |  ||  | |(`-')|'--...__)'`'-..__| OO).-.  '|  |\\  |  ||   \\ |  |","\\_..`--.|  `-'  ||  | |(OO )`--.  .--'|  |  ' ( _) | |  ||  | '.|  ||  . '|  |)",".-._)   \\  .-.  ||  | | |  \\   |  |   |  |  / :\\|  |)|  ||  |.'.|  ||  |\\    |","\\       /  | |  |\\  '-'(_ .'   |  |   |  '-'  / '  '-'  '|   ,'.   ||  | \\   |"," `-----'`--' `--' `-----'      `--'   `------'   `-----' `--'   '--'`--'  `--'"}),
	Morse2		(new String[]{"... .... ..- - -.. --- .-- -."}),
	Octal		(new String[]{"163 150 165 164 144 157 167 156"}),
	Ogre		(new String[]{"     _           _      _"," ___| |__  _   _| |_ __| | _____      ___ __","/ __| '_ \\| | | | __/ _` |/ _ \\ \\ /\\ / / '_ \\","\\__ \\ | | | |_| | || (_| | (_) \\ V  V /| | | |","|___/_| |_|\\__,_|\\__\\__,_|\\___/ \\_/\\_/ |_| |_|"}),
	Roman		(new String[]{"        oooo                      .        .o8","        `888                    .o8       \"888"," .oooo.o 888 .oo.  oooo  oooo .o888oo .oooo888  .ooooo. oooo oooo    ooo ooo. .oo.","d88(  \"8 888P\"Y88b `888  `888   888  d88' `888 d88' `88b `88. `88.  .8'  `888P\"Y88b","`\"Y88b.  888   888  888   888   888  888   888 888   888  `88..]88..8'    888   888","o.  )88b 888   888  888   888   888 .888   888 888   888   `888'`888'     888   888","8\"\"888P'o888o o888o `V88V\"V8P'  \"888\"`Y8bod88P\"`Y8bod8P'    `8'  `8'     o888o o888o"}),
	Soft		(new String[]{"      ,--.               ,--.    ,--."," ,---.|  ,---. ,--.,--.,-'  '-.,-|  | ,---. ,--.   ,--.,--,--,","(  .-'|  .-.  ||  ||  |'-.  .-' .-. || .-. ||  |.'.|  ||      \\",".-'  `)  | |  |'  ''  '  |  | \\ `-' |' '-' '|   .'.   ||  ||  |","`----'`--' `--' `----'   `--'  `---'  `---' '--'   '--'`--''--'"}),
	Speed		(new String[]{"       ______        ______________","__________  /_____  ___  /______  /________      ________","__  ___/_  __ \\  / / /  __/  __  /_  __ \\_ | /| / /_  __ \\","_(__  )_  / / / /_/ // /_ / /_/ / / /_/ /_ |/ |/ /_  / / /","/____/ /_/ /_/\\__,_/ \\__/ \\__,_/  \\____/____/|__/ /_/ /_/"}),
	Stop		(new String[]{"      _                   _","     | |          _      | |","  ___| | _  _   _| |_  _ | | ___  _ _ _ ____"," /___) || \\| | | |  _)/ || |/ _ \\| | | |  _ \\","|___ | | | | |_| | |_( (_| | |_| | | | | | | |","(___/|_| |_|\\____|\\___)____|\\___/ \\____|_| |_|"}),
	Swampland	(new String[]{" ______  ___   ___  __  __  _________ ______  ______  __ __ __  ___   __","/_____/\\/__/\\ /__/\\/_/\\/_/\\/________/Y_____/\\/_____/\\/_//_//_/\\/__/\\ /__/\\","\\::::_\\/\\::\\ \\\\  \\ \\:\\ \\:\\ \\__.::.__\\|:::_ \\ \\:::_ \\ \\:\\\\:\\\\:\\ \\::\\_\\\\  \\ \\"," \\:\\/___/\\::\\/_\\ .\\ \\:\\ \\:\\ \\ \\::\\ \\  \\:\\ \\ \\ \\:\\ \\ \\ \\:\\\\:\\\\:\\ \\:. `-\\  \\ \\","  \\_::._\\:\\:: ___::\\ \\:\\ \\:\\ \\ \\::\\ \\  \\:\\ \\ \\ \\:\\ \\ \\ \\:\\\\:\\\\:\\ \\:. _    \\ \\","    /____\\:\\: \\ \\\\::\\ \\:\\_\\:\\ \\ \\::\\ \\  \\:\\/.:| \\:\\_\\ \\ \\:\\\\:\\\\:\\ \\. \\`-\\  \\ \\","    \\_____\\/\\__\\/ \\::\\/\\_____\\/  \\__\\/   \\____/_/\\_____\\/\\_______\\/\\__\\/ \\__\\/"}),
	Swan		(new String[]{"    .         .     .","    |        _|_    |",".--.|--. .  . |  .-.| .-.  .    ._.--.","`--.|  | |  | | (   |(   )  \\  /  |  |","`--''  `-`--`-`-'`-'`-`-' `' `'   '  `-"}),
	Varsity		(new String[]{"       __               _         __","      [  |             / |_      |  ]"," .--.  | |--.  __   _ `| |-' .--.| |  .--.  _   _   __ _ .--.","( (`\\] | .-. |[  | | | | | / /'`\\' |/ .'`\\ [ \\ [ \\ [  | `.-. |"," `'.'. | | | | | \\_/ |,| |,| \\__/  || \\__. |\\ \\/\\ \\/ / | | | |","[\\__) )___]|__]'.__.'_/\\__/ '.__.;__]'.__.'  \\__/\\__/ [___||__]"}),
	;

	/** Lines for the figlet. */
	private String[] lines;

	/** Returns a new Figlet for shutdown messages. */
	private FigletShutdown(String[] lines){
		this.lines = lines;
	}

	/**
	 * Returns an array with all defined FigLets lines for one font.
	 * @return a single message as array
	 */
	public String[] getLines(){
		return this.lines;
	}

	/**
	 * Generates a new random to randomly select a figlet.
	 */
	public final static Random random = new Random();

	/**
	 * Returns a random array of FigLet message saying "shutdown". 
	 * @return random array for message
	 */
	public static String[] randomLines(){
		return FigletShutdown.values()[FigletShutdown.random.nextInt(FigletShutdown.values().length)].lines;
	}

}
