package io.itdraft.levenshteinautomaton.description.parametric.coding;

import io.itdraft.levenshteinautomaton.description.parametric.coding.util.UIntPackedArray;

import java.util.HashMap;
import java.util.Map;

public class EncodedParametricDscr {
    private static final Map<Integer, EncodedParametricDscr> STANDARD_BY_DEGREE =
            new HashMap<>();
    private static final Map<Integer, EncodedParametricDscr> INCL_TRANSPOSITIONS_BY_DEGREE =
            new HashMap<>();

    static {
        STANDARD_BY_DEGREE.put(1, new EncodedParametricDscr(5,
                new UIntPackedArray(3, new long[]{0xbb6d56922db55b69L, 0x6ab6d45220924526L, 0x848249051ae93593L, 0x11a20a444L}),
                new UIntPackedArray(2, new long[]{0x5554a00001500000L, 0x55557a0283c00015L, 0x155555L}),
                new int[]{0, 1, 0, -1, -1}
        ));

        STANDARD_BY_DEGREE.put(2, new EncodedParametricDscr(30,
                new UIntPackedArray(5, new long[]{0xef7bdef7bdef78a1L, 0xdef7bdef7bdef7bdL, 0xbdef7bc630bdef7bL, 0x7bdef7bdef7bdef7L, 0xf0420f7bdef7bdefL,
                        0xef7bdef7bdef14beL, 0xdef7bdef7bdef7bdL, 0xbc6f17def78e730bL, 0x7bdef7bdef7bde3fL, 0x297de420e3f7bdefL, 0xef7bdef78fef1fc5L,
                        0x21083def7bdef7bdL, 0xbde0fbc1f18a52f8L, 0x7bdef7bdef7bde2fL, 0xf07c6318be10820fL, 0xef7bdef78bef783eL, 0xdef1d2730bdef7bdL,
                        0xbdef1bde498a6f1bL, 0xa4c429bdef7bdef7L, 0xf79612d0a12fbc95L, 0x6f7bdef7bde2f8b4L, 0xc7298a5f210838caL, 0xbdef17def1fde498L,
                        0x17c8620e329fdef7L, 0x2f8a9f7961324c13L, 0x1080a7f7bdef7bc5L, 0xde51e2129ca52854L, 0x7def7bdef14a5287L, 0xaa28894aa6a82088L,
                        0xf7bc58962af79a88L, 0x62884208221f7bdeL, 0xa5287de51ce131ccL, 0x820887def7bdef18L, 0x79a83a8e8398a218L, 0xf7bdef7bc68962afL,
                        0x6f1bdef1d2730a21L, 0xdef7bdef18de49ccL, 0xbdb71cc231bdef7bL, 0x97d9395c8098a7f7L, 0x31039f17c5f17c52L, 0x5584d4284bef2569L,
                        0xbef14bef78be2d02L, 0xc12fbce5a4c4318L, 0x97c5314ba099620dL, 0xf210838c39297c62L, 0xef1cc549ce7298a5L, 0xe331ca52fbdef17dL,
                        0x5c841e27297cf7a0L, 0xc4a5f17d1297db3cL, 0x304c5f218838c3b2L, 0xef14be2a426584e9L, 0xcf620e331cc5294bL, 0x4ae09d62424e1317L,
                        0x803b344a6297d131L, 0x5266129ca5285410L, 0x5294a5294a5284e5L, 0x49294bc80420984aL, 0x8b2948bc4ca0be86L, 0xa9aa083bc2c8b22cL,
                        0x8a9116a26a8a2252L, 0x986252c62529625L, 0xaceaa68894b06a82L, 0x8b38c8b29ca3044dL, 0x1cc628842083bc8cL, 0x298a5284e6525213L,
                        0x420820984c6314a5L, 0xbc4d20b58689318aL, 0x3bc350d22c8b4948L, 0xa12a3a0e62886208L, 0x3462529a258a9076L, 0x8398a418820984e6L,
                        0xb49ca30425ac5a92L, 0xd2730bbc3d0d38c8L, 0x18de49cc6f1bdef1L, 0x31bdef7bdef7bdefL, 0x939bc6f7bc749cc2L, 0xa5294bef7bc6a152L,
                        0xfdef6dc7308e6294L, 0x14a5f64e57202629L, 0x71cc20e7c5f17c5fL, 0x9499c8098a7f7bdbL, 0x39298a6298a5297dL, 0x4d4284bef2569310L,
                        0x14bef78be2d02558L, 0x2fbc95a4c4318befL, 0xe2f8b45456a3d0a1L, 0x69310e6894b1894bL, 0x82658834304bef39L, 0xe4a5f18a5f14c52eL,
                        0xd0c12fbce5a4c40L, 0x7898a5314ba51d62L, 0xa5f210838c39898aL, 0x7def1cc549ce7298L, 0x20e331ca52fbdef1L, 0xa492949ca6297c84L,
                        0x794a45294a5f7bc7L, 0x789ca5f3de838ceL, 0x7c5f44a5f6cf1721L, 0x97cf7a0e30ecb129L, 0x297db4cdc841e272L, 0x838c3b94e46298b1L,
                        0x26584e9304c5f218L, 0xcc5294bef14be2a4L, 0xa4c1317c8620e331L, 0x894a52f8a95516a4L, 0x5f3d8838ce7a4a51L, 0xc52b82758909384cL,
                        0xe30ecd1298a5f44L, 0x2562424e1317cf62L, 0xa4e47898b1314ae5L, 0x129ca5285410803bL, 0xa5294a5284e55266L, 0x4a150420984a5294L,
                        0x94a1dd54ec04a729L, 0x8301ad6b5ad6a52L, 0x282fa1924a52f201L, 0xb22c8b22ca522f13L, 0x649294bc80420ef0L, 0xdab2948bc75a0be8L,
                        0x2a9aa083bcadab6aL, 0x58a9116a26a8a225L, 0x20986252c6252962L, 0xdafc2a28894aa6a8L, 0xd6b7bd6a58962a7dL, 0x9a2252c1aa08301bL,
                        0x22ca728c1136b3aaL, 0xb06a820ef2322ce3L, 0xa307e1aceaa68894L, 0x83bcbdab8bdab29cL, 0x5252131cc6288420L, 0x6314a5298a5284e6L,
                        0xc7318a210820984cL, 0x6a6294a1de54e704L, 0x908208301ce735adL, 0x2f13482d61a24c62L, 0xef0d4348b22d252L, 0xb58689318a42082L,
                        0xb36adab4948bc76aL, 0xa0e628862083bcceL, 0x29a258a9076a12a3L, 0x218820984e634625L, 0x2a7edaf728e8398aL, 0x301de737bd6a6896L,
                        0xb16a4a0e62906208L, 0x34e322d2728c1096L, 0x8398a418820ef0f4L, 0xb49ca307b9ac5a92L, 0x3bcdeb38bdaL}),
                new UIntPackedArray(3, new long[]{0x0L, 0x0L, 0x490000000000000L, 0x48L, 0x8000000000L, 0x4800000000000000L, 0x2L, 0x20020104920924L,
                        0x412482490000000L, 0x8008L, 0x9808000L, 0x361b6000006000L, 0x2000018000000618L, 0x8000009049L, 0x6180034134800006L,
                        0x9249249258000002L, 0x4924000004924024L, 0x12490092492492L, 0x249249249249000L, 0x4924924000004924L, 0x12490092492L,
                        0x10098080000009L, 0x2200000060000000L, 0x201041049005028L, 0x801861b0d86d8000L, 0xc381b6000006180dL, 0x207037047186L,
                        0x2400080012090492L, 0x48241248000061L, 0x4d20000204941029L, 0x6124d809861a0d0L, 0x271868341348000L, 0x2492492492604937L,
                        0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L,
                        0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x8000000924924924L, 0x6000000000100980L,
                        0x8000542602000000L, 0xa0880000018b6db6L, 0x8041041240140L, 0xcb2c900502822000L, 0x1b0d86d8000020b2L, 0x6000006180d80186L,
                        0x876b7686186c361bL, 0x1c61b0e06d800001L, 0x81b60000081c0dc1L, 0x2072b72c7186c3L, 0x8001209049200L, 0x4824124800006124L,
                        0x2000018495b6a000L, 0x125040a401209049L, 0x9004824124800008L, 0x4d2000020494b2aL, 0x6124d809861a0dL, 0x76a6186834134800L,
                        0xa0d04d2000018493L, 0x800008124dc09c61L, 0x49372a7186834134L, 0x9249249249249260L, 0x4924924924924924L, 0x2492492492492492L,
                        0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L,
                        0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L,
                        0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L,
                        0x9249249249249249L, 0x924924924L}),
                new int[]{0, 1, 0, -1, -1, 2, 1, 0, -1, -1, 0, -2, -2, -1, -2, -2, -1, 0, -1, -1,
                        -1, -2, -2, -2, -2, -2, -2, -2, -1, -2}
        ));

        INCL_TRANSPOSITIONS_BY_DEGREE.put(1, new EncodedParametricDscr(6,
                new UIntPackedArray(3, new long[]{0x6cb6c48db6cb6db1L, 0x8a488251231273dbL, 0x473c49cc93b2db6cL, 0x829222d84825120aL, 0x2c88L}),
                new UIntPackedArray(2, new long[]{0x52a0000015000000L, 0xa02a0fc000055555L, 0x555555555555eL}),
                new int[]{0, 1, 0, -1, -1, -1}
        ));

        INCL_TRANSPOSITIONS_BY_DEGREE.put(2, new EncodedParametricDscr(42,
                new UIntPackedArray(6, new long[]{0xaaaaaaaaaaaaa141L, 0xaaaaaaaaaaaaaaaaL, 0xaaaaaaaaaaaaaaaaL, 0x2aaaaaaaaaaaaaaaL, 0xaaaaaaaaaaaa8618L,
                        0xaaaaaaaaaaaaaaaaL, 0xaaaaaaaaaaaaaaaaL, 0x40aaaaaaaaaaaaaaL, 0xaaaaaaa8516aa810L, 0xaaaaaaaaaaaaaaaaL, 0xaaaaaaaaaaaaaaaaL,
                        0x182aaaaaaaaaaaaaL, 0xa1aa16aaaaaaa208L, 0xaaaaaaaaaaaaaaaaL, 0x22aaaaaaaaaaaaaaL, 0x91c3aaaaaaaaa22aL, 0xaa1ea145145aaa24L,
                        0xaaaaaaaaaaaaaaaaL, 0xa26aaaaaaaaaaaaaL, 0x81040aaaaaaaaa1eL, 0xa8506a18518516a0L, 0xaaaaaaaaaaaaaaaaL, 0x6a06aaaaaaaaaaaaL,
                        0x82040aaaaaaaaa0L, 0xaa8506a18618616aL, 0xaaaaaaaaaaaaaaaaL, 0x6a0aaaaaaaaaaaaL, 0x83c8182aaaaaaaaaL, 0xaaaaa1aa1aaaaaa8L,
                        0xaaaa861aaaaaaaaaL, 0x63ea3c616aaaaaaaL, 0x8a7ca184a85a85a8L, 0x145a8540504516aaL, 0xaaaaa901aaaaa16aL, 0x907ea7c116aaaaaaL,
                        0xa892491c3a85a85aL, 0x516aaaa1c6185145L, 0xaaaaaa871eaaaa14L, 0xa9146a2461aaaaaaL, 0x5a898091c3a85a86L, 0x85185a8544604618L,
                        0xaaaaaaa911eaaaa1L, 0x6a919aa8011aaaaaL, 0x85141081040a85a8L, 0x1851451450452051L, 0xaaaaaaaa8106aaaaL, 0x88a812ea2c832aaaL,
                        0x20514b84b040a8caL, 0xa20530c14c2cc24cL, 0xaaaaaaaaa8b06aaaL, 0xa88a8b8ea8c932aaL, 0x6186142082040a8cL, 0xaa18618514504820L,
                        0xaaaaaaaaaa8106aaL, 0xca88a8b2ea08822aL, 0x48206142842040a8L, 0xaaa20620c14c2c82L, 0xaaaaaaaaaaa8b06aL, 0x8ca88a8b8ea84922L,
                        0x1aaaaaa883c8182aL, 0x6aaaaaaaaaaaa1aaL, 0xaaaaaaaaaaaa8618L, 0xa86a86a863ea3c81L, 0x51eaaaaa92888182L, 0x4716a14514516a18L,
                        0x6a145145aaaa996L, 0x4141141159885889L, 0x504516aa8a7ca18L, 0x18114516a145a854L, 0x1185a8516aa85a90L, 0x841861861507c67cL,
                        0x40604516aa937ca1L, 0xd641185185186145L, 0xc2045146185a85a9L, 0x1c318118119d7c67L, 0xa1c6185145a89249L, 0x871c618514516aaaL,
                        0x248205145aaa16aaL, 0x91c3a86148151445L, 0x6a1c81c5145a9450L, 0xa9e78720530c3051L, 0xc50924530c14516aL, 0x91c31413093259cL,
                        0xa85446046185a898L, 0x5a911c1186185185L, 0x8880120614616a14L, 0x8091c31862081919L, 0x6145448046185a94L, 0x45aa578120620c20L,
                        0x9888022463081851L, 0x1081040181209225L, 0x4514504520518514L, 0x1451410481451851L, 0x12c52cf386145145L, 0x5510104014e18f18L,
                        0x34d34504d2851851L, 0xd14515554a34540dL, 0x1590d91fa0634d34L, 0x14b84b04036841f4L, 0x530c14c2cc24c205L, 0x514c14b04930c20L,
                        0x20b8ce8c938814c3L, 0x515684b04038e3cfL, 0xcd38e34c2ce24c20L, 0x38d14c15654938c3L, 0xf3d68ce8e0a0834eL, 0x861420820403a83dL,
                        0x1861851450482061L, 0x6145185141048206L, 0x8f18b2c608f3c618L, 0x18614410204014e1L, 0x641040d34504f286L, 0x1034d18515554a3cL,
                        0x41f41691011f7c64L, 0x8206142842040368L, 0x820620c14c2c824L, 0x18830518c14b0492L, 0xe3cf20b8cf8493c8L, 0x4820614484204038L,
                        0x3c83d03ce34c2cf2L, 0x840f38d18c156549L, 0xa83df3d68cf8607cL, 0x1aaaaaa883c81823L, 0x6aaaaaaaaaaaa1aaL, 0xaaaaaaaaaaaa8618L,
                        0xa86a86a863ea3c81L, 0xa1aaaaaa883c8182L, 0x90145aaaaaaaaa1aL, 0x1c51451451451461L, 0x21471471463c53caL, 0x851eaaaaa9288818L,
                        0x64716a14514516a1L, 0x906a145145aaaa99L, 0x8214114115988588L, 0x1851eaaaaa928881L, 0x965118514514516aL, 0x8904518618614515L,
                        0x1841811811998868L, 0x540504516aa8a7caL, 0x9018114516a145a8L, 0x7c1185a8516aa85aL, 0xa1841861861507c6L, 0x8540504516aa8a7cL,
                        0x15018b30c16a145aL, 0x87cb1cc14c30514cL, 0xca1842072073107cL, 0x14540604516aa937L, 0x5a9d641185185186L, 0xc67c2045146185a8L,
                        0x7ca18418118119d7L, 0x614540604516aa93L, 0x4c15d64b20c18518L, 0x7c87c204c1882061L, 0x92491c320120121dL, 0x6aaaa1c6185145a8L,
                        0x16aa871c61851451L, 0x1445248205145aaaL, 0x892491c3a8614815L, 0x16aaaa1c6185145aL, 0x53451471d040d145L, 0x5144d24a28d34d14L,
                        0xa945091c314734a3L, 0xc30516a1c81c5145L, 0x4516aa9e78720530L, 0x3259cc50924530c1L, 0x5a945091c3141309L, 0xc30516a1c81c514L,
                        0x18634515e7913cd3L, 0x93a59ce50924d38eL, 0x85a898091c318138L, 0x185185a854460461L, 0x616a145a911c1186L, 0x819198880120614L,
                        0x185a898091c31862L, 0x185185a85446046L, 0x5030534c1511cb41L, 0x3ca41198f80b2903L, 0x6185a948091c3207L, 0x620c20614544804L,
                        0x308185145aa57812L, 0x1209225988802246L, 0x46185a948091c318L, 0x3d020c2061454480L, 0x38f20634c16578bL, 0x13c93e598f80225L,
                        0x2051851410810402L, 0x8145185145145045L, 0x8614514514514104L, 0x14e18f1812c52cf3L, 0x5205185141081040L, 0x525d718514514504L,
                        0xa595d75d75d75c10L, 0x5e96626412d72e2L, 0x4d28518515510104L, 0x54a34540d34d3450L, 0xfa0634d34d145155L, 0x4036841f41590d91L,
                        0x4d2851851551010L, 0x555369740d34d345L, 0x1fa1969a69a5d75dL, 0x406a875f75591a9L, 0xc2cc24c20514b84bL, 0x4b04930c20530c14L,
                        0x8c938814c30514c1L, 0xb04038e3cf20b8ceL, 0x4c2cc24c20514b84L, 0x5cb05461820530c1L, 0xb8d4a525d86175d8L, 0x4b0406e972248b8dL,
                        0x34c2ce24c2051568L, 0xc15654938c3cd38eL, 0xce8e0a0834e38d14L, 0x84b0403a83df3d68L, 0xe34c2ce24c205156L, 0xd85d65546d83cd38L,
                        0x8db8e0a1269b6da5L, 0x20820406e871f716L, 0x8514504820618614L, 0x1851410482061861L, 0xb2c608f3c6186145L, 0x4208204014e18f18L,
                        0x1851450482061861L, 0x76575c1052499186L, 0x4b2d90a28996595dL, 0x1441020405e96626L, 0x40d34504f286186L, 0x4d18515554a3c641L,
                        0x41691011f7c64103L, 0x614410204036841fL, 0x1040d34504f28618L, 0x69a6575d55537194L, 0xf75691d11f7d975dL, 0x61428420406a875L,
                        0x20620c14c2c82482L, 0x830518c14b049208L, 0xcf20b8cf8493c818L, 0x20614284204038e3L, 0x220620c14c2c8248L, 0x526176585cb05449L,
                        0x72248b8dc8548926L, 0x82061448420406e9L, 0xc83d03ce34c2cf24L, 0x40f38d18c1565493L, 0x83df3d68cf8607c8L, 0x482061448420403aL,
                        0x7123d03ce34c2cf2L, 0x275c6da6585d6554L, 0xe871f7168dc8607dL, 0x6L}),
                new UIntPackedArray(3, new long[]{0x0L, 0x0L, 0x0L, 0x9000000000000000L, 0x4804L, 0x0L, 0x400000L, 0x0L, 0x124800L, 0x9240000000000000L,
                        0x4824920L, 0x2490000082000000L, 0x1209248L, 0x20800000L, 0x4000L, 0xc3000098000L, 0x186c30db60L, 0x30c00036000L, 0x490002492L,
                        0x800000c200009000L, 0x124c309a4L, 0x9249243080003400L, 0x120024924924L, 0x2492490410482480L, 0x48009249249L, 0x4924924104120920L,
                        0x12002492492L, 0x9249249041048248L, 0x4800924924L, 0x4000000010412092L, 0x8000000080000000L, 0x100000000c300009L, 0x8249000028249201L,
                        0x36d8000024920102L, 0xdb0d80c00db61b0cL, 0xdb6000006db6030L, 0x38e37030036dc6e3L, 0x249200001c7180cL, 0x924804000924900L,
                        0x924800000d2402L, 0x8249291000249252L, 0xc26920000244900L, 0x20d24984c00d2493L, 0xe309a4800006d240L, 0x834927130034924L,
                        0x924924924925c490L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L,
                        0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L,
                        0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x8000000040000000L, 0xc30000980000000L,
                        0x2b40000010000000L, 0x2bae814276db6da0L, 0xa09248044000000L, 0x9248040a0924000L, 0x296492011000000L, 0x2492010296492daL,
                        0xdb61b0c36d8000L, 0x6db6030db0d80cL, 0xa036d86c30db6000L, 0x1b6d80c36eb76bL, 0xc00db71b8c36d800L, 0x71c6030e38dc0L,
                        0xba036dc6e30db600L, 0x1c7180c38e372L, 0x4000924900024920L, 0xd2402092480L, 0x55a0249240009248L, 0x2b490082492bL, 0x4400092494802492L,
                        0x800009124020924aL, 0x915a024925200924L, 0x2000024490082492L, 0x984c00d24930c269L, 0x4800006d24020d24L, 0x2753a034924c309aL,
                        0x9200001b49008349L, 0x49c4c00d24938c26L, 0xa4800007124020d2L, 0x92713a034924e309L, 0x24924925c4900834L, 0x9249249249249249L,
                        0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L,
                        0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L,
                        0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L,
                        0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L,
                        0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x0L}),
                new int[]{0, 1, 0, -1, -1, 2, 1, 0, 0, -1, -1, 0, 0, -1, -1, -1, -1, -1, -2, -2, -2, -1, -1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2,
                        -1, -1, -2, -2, -2, -2, -2}
        ));
    }

    public static EncodedParametricDscr get(int degree, boolean inclTranspositions) {
        return inclTranspositions ?
                EncodedParametricDscr.getInclTranspositions(degree) :
                EncodedParametricDscr.getStandard(degree);
    }

    public static EncodedParametricDscr getStandard(int automatonDegree) {
        return STANDARD_BY_DEGREE.get(automatonDegree);
    }

    public static EncodedParametricDscr getInclTranspositions(int automatonDegree) {
        return INCL_TRANSPOSITIONS_BY_DEGREE.get(automatonDegree);
    }

    private int statesCount;
    private UIntPackedArray transitions;
    private UIntPackedArray boundaryOffsets;
    private int[] degreeMinusStateLength;

    public EncodedParametricDscr(int statesCount,
                                 UIntPackedArray transitions,
                                 UIntPackedArray boundaryOffsets,
                                 int[] degreeMinusStateLength) {
        this.statesCount = statesCount;
        this.transitions = transitions;
        this.boundaryOffsets = boundaryOffsets;
        this.degreeMinusStateLength = degreeMinusStateLength;
    }

    public int getStatesCount() {
        return statesCount;
    }

    public UIntPackedArray getTransitions() {
        return transitions;
    }

    public UIntPackedArray getBoundaryOffsets() {
        return boundaryOffsets;
    }

    public int[] getDegreeMinusStateLength() {
        return degreeMinusStateLength;
    }
}
