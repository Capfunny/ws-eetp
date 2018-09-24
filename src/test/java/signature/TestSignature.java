package signature;

import com.objsys.asn1j.runtime.*;
import org.bouncycastle.util.encoders.Base64;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.ContentInfo;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.SignedData;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.SignerInfo;
import ru.CryptoPro.JCP.ASN.PKIX1Explicit88.Attribute;
import ru.CryptoPro.JCP.JCP;
import ru.CryptoPro.JCP.params.OID;
import ru.CryptoPro.JCP.tools.Array;
import ru.CryptoPro.JCP.tools.Decoder;
import ru.bm.eetp.config.Utils;
import ru.bm.eetp.signature.SignatureUtils;

import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class TestSignature {

    private static final String document_rad  = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz48dG5zOkZ1bmRzSG9sZFJxIHhtbG5zOnRucz0iaHR0cDovL3d3dy5zYmVyYmFuay5ydS9lZG8vb2VwL2Vkby1vZXAtcHJvYyIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hLWluc3RhbmNlIj48dG5zOk1zZ0lEPjY1NDAyMGJjYWNmNTExZThiMTFlNzY3MzY0NzY3MzY0PC90bnM6TXNnSUQ+PHRuczpNc2dUbT4yMDE4LTA4LTMxVDExOjExOjAwKzAzOjAwPC90bnM6TXNnVG0+PHRuczpPcGVyYXRvck5hbWU+RVRQX1JBRDwvdG5zOk9wZXJhdG9yTmFtZT48dG5zOkFwcElEPjEyMzQ8L3RuczpBcHBJRD48dG5zOkJhbmtJRD5TQlI8L3RuczpCYW5rSUQ+PHRuczpFbnRyeUNsb3NlVG0+MjAxOC0wOC0wOVQwOToxMDozNC44NTUrMDM6MDA8L3RuczpFbnRyeUNsb3NlVG0+PHRuczpOYW1lPmFiYzwvdG5zOk5hbWU+PHRuczpJTk4+MTIzNDU2Nzg5MDEyPC90bnM6SU5OPjx0bnM6S1BQPjEyMzQ1Njc4OTwvdG5zOktQUD48dG5zOkFjY291bnQ+MTIzNDU2Nzg5MDEyMzQ1Njc4OTA8L3RuczpBY2NvdW50Pjx0bnM6QW1vdW50VG9Ib2xkeDEwMD4xMDAwPC90bnM6QW1vdW50VG9Ib2xkeDEwMD48L3RuczpGdW5kc0hvbGRScT4=";
    private static final String document_vtb  = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/Pgo8RnVuZHNIb2xkUnMgeG1sbnM9Imh0dHA6Ly93d3cuc2JlcmJhbmsucnUvZWRvL29lcC9lZG8tb2VwLXByb2MiPgoJPE1zZ0lEPmE1OTQ0NTBkMTE3ODQ4YWM4ZmY1NjllYjI3NjM5N2FhPC9Nc2dJRD4KCTxNc2dUbT4yMDE4LTA4LTA5VDA5OjEwOjM0Ljg1NSswMzowMDwvTXNnVG0+Cgk8Q29ycmVsYXRpb25JRD4xMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAxMDwvQ29ycmVsYXRpb25JRD4KCTxPcGVyYXRvck5hbWU+RVRQX01NVkI8L09wZXJhdG9yTmFtZT4KCTxBcHBJRD4xMDEwPC9BcHBJRD4KCTxCYW5rSUQ+VlRCPC9CYW5rSUQ+Cgk8U3RhdHVzPgoJCTxTdGF0dXNDb2RlPjA8L1N0YXR1c0NvZGU+Cgk8L1N0YXR1cz4KPC9GdW5kc0hvbGRScz4=";
    private static final String document_goz  = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxQYXJ0eUNoZWNrUnEgeG1sbnM9Imh0dHA6Ly93d3cuc2JlcmJhbmsucnUvZWRvL29lcC9lZG8tb2VwLXByb2MiPjxNc2dJRD5iMGRmMGNmOWRkZjU0YjQ5YmU1ZDRlZGRlZmY4M2Q4NjwvTXNnSUQ+PE1zZ1RtPjIwMTgtMDktMTdUMTI6NTM6MDEuMzg1KzAzOjAwPC9Nc2dUbT48T3BlcmF0b3JOYW1lPkVUUF9BU1RHT1o8L09wZXJhdG9yTmFtZT48QmFua0lEPlZUQjwvQmFua0lEPjxDbGllbnRUeXBlPjE8L0NsaWVudFR5cGU+PElOTj43NzI3ODM3Nzk2PC9JTk4+PEtQUD43NzI3MDEwMDE8L0tQUD48T0dSTj4xMTQ3NzQ2NzI1NDY2PC9PR1JOPjwvUGFydHlDaGVja1JxPg==";
    private static final String document_mmvb  = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/Pgo8RnVuZHNIb2xkUnMgeG1sbnM9Imh0dHA6Ly93d3cuc2JlcmJhbmsucnUvZWRvL29lcC9lZG8tb2VwLXByb2MiPgoJPE1zZ0lEPmE1OTQ0NTBkMTE3ODQ4YWM4ZmY1NjllYjI3NjM5N2FhPC9Nc2dJRD4KCTxNc2dUbT4yMDE4LTA4LTA5VDA5OjEwOjM0Ljg1NSswMzowMDwvTXNnVG0+Cgk8Q29ycmVsYXRpb25JRD4xMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAxMDwvQ29ycmVsYXRpb25JRD4KCTxPcGVyYXRvck5hbWU+RVRQX01NVkI8L09wZXJhdG9yTmFtZT4KCTxBcHBJRD4xMDEwPC9BcHBJRD4KCTxCYW5rSUQ+VlRCPC9CYW5rSUQ+Cgk8U3RhdHVzPgoJCTxTdGF0dXNDb2RlPjA8L1N0YXR1c0NvZGU+Cgk8L1N0YXR1cz4KPC9GdW5kc0hvbGRScz4=";
    private static final String signature_rad = "MIAGCSqGSIb3DQEHAqCAMIACAQExDjAMBggqhQMHAQECAgUAMIAGCSqGSIb3DQEHAQAAoIIE7jCCBOowggSZoAMCAQICExIAKpuphi/Gq2XY55cAAAAqm6kwCAYGKoUDAgIDMH8xIzAhBgkqhkiG9w0BCQEWFHN1cHBvcnRAY3J5cHRvcHJvLnJ1MQswCQYDVQQGEwJSVTEPMA0GA1UEBxMGTW9zY293MRcwFQYDVQQKEw5DUllQVE8tUFJPIExMQzEhMB8GA1UEAxMYQ1JZUFRPLVBSTyBUZXN0IENlbnRlciAyMB4XDTE4MDcxMDA3NTcxOVoXDTE4MTAxMDA4MDcxOVowggFJMRgwFgYFKoUDZAESDTEwOTc4NDcyMzMzNTExGDAWBggqhQMDgQMBARIKNzgzODQzMDQxMzEgMB4GCSqGSIb3DQEJARYRb3BlcmF0b3JAdGVzdDEucnUxCzAJBgNVBAYTAlJVMRswGQYDVQQIDBLQmtC40YDQvtCy0YHQutCw0Y8xEzARBgNVBAcMCtCa0LjRgNC+0LIxFjAUBgNVBAoMDdCe0J7QniDQrdCi0J8xOzA5BgNVBAMMMtCe0L/QtdGA0LDRgtC+0YAg0J7Qv9C10YDQsNGC0L7RgCDQntC/0LXRgNCw0YLQvtGAMRYwFAYDVQQJDA3Rg9C7INCc0LjRgNCwMSowKAYDVQQqDCHQntC/0LXRgNCw0YLQvtGAINCe0L/QtdGA0LDRgtC+0YAxGTAXBgNVBAQMENCe0L/QtdGA0LDRgtC+0YAwZjAfBggqhQMHAQEBATATBgcqhQMCAiMBBggqhQMHAQECAgNDAARACvFxVGVB9C0JHLa6YDhtQ/0m3aDy/pfAHlO5A8wwC2nb6GLVdlu3oLd6OxFCT+2t73ScrmE+1FY0fLzbYyNp0qOCAhswggIXMA4GA1UdDwEB/wQEAwIF4DBVBgNVHSUETjBMBggrBgEFBQcDAgYIKwYBBQUHAwQGDSqFAwM9ntc2AQYDBAcGDiqFAwM9ntc2AQYDBAcBBg4qhQMDPZ7XNgEGAwQHAgYHKoUDBgMBATBnBgkrBgEEAYI3FQoEWjBYMAoGCCsGAQUFBwMCMAoGCCsGAQUFBwMEMA8GDSqFAwM9ntc2AQYDBAcwEAYOKoUDAz2e1zYBBgMEBwEwEAYOKoUDAz2e1zYBBgMEBwIwCQYHKoUDBgMBATAdBgNVHQ4EFgQUG96FHRPvXIzSrFHDhTUDFnrCL2MwHwYDVR0jBBgwFoAUFTF8sI0a3mbXFZxJUpcXJLkBeoMwWQYDVR0fBFIwUDBOoEygSoZIaHR0cDovL3Rlc3RjYS5jcnlwdG9wcm8ucnUvQ2VydEVucm9sbC9DUllQVE8tUFJPJTIwVGVzdCUyMENlbnRlciUyMDIuY3JsMIGpBggrBgEFBQcBAQSBnDCBmTBhBggrBgEFBQcwAoZVaHR0cDovL3Rlc3RjYS5jcnlwdG9wcm8ucnUvQ2VydEVucm9sbC90ZXN0LWNhLTIwMTRfQ1JZUFRPLVBSTyUyMFRlc3QlMjBDZW50ZXIlMjAyLmNydDA0BggrBgEFBQcwAYYoaHR0cDovL3Rlc3RjYS5jcnlwdG9wcm8ucnUvb2NzcC9vY3NwLnNyZjAIBgYqhQMCAgMDQQDYfhAoMTxJe1lO6sHZIaEQXjzB6FxIMsbZ9qRd+M4NtwUW96b5ydaMrCgR83+IT8Bj9gDIJ5bTtksM06j4NbrzMYICVDCCAlACAQEwgZYwfzEjMCEGCSqGSIb3DQEJARYUc3VwcG9ydEBjcnlwdG9wcm8ucnUxCzAJBgNVBAYTAlJVMQ8wDQYDVQQHEwZNb3Njb3cxFzAVBgNVBAoTDkNSWVBUTy1QUk8gTExDMSEwHwYDVQQDExhDUllQVE8tUFJPIFRlc3QgQ2VudGVyIDICExIAKpuphi/Gq2XY55cAAAAqm6kwDAYIKoUDBwEBAgIFAKCCAVIwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTgwOTE4MDUzOTI5WjAvBgkqhkiG9w0BCQQxIgQg367lUs8uWViDDCIRcD1rwEGPwXw2UKmv1y1trdOJjH4wgeYGCyqGSIb3DQEJEAIvMYHWMIHTMIHQMIHNMAoGCCqFAwcBAQICBCBGhSexLQyDIoYddzjgjIIlFBxYAQBJYVVs9sab8aLR2DCBnDCBhKSBgTB/MSMwIQYJKoZIhvcNAQkBFhRzdXBwb3J0QGNyeXB0b3Byby5ydTELMAkGA1UEBhMCUlUxDzANBgNVBAcTBk1vc2NvdzEXMBUGA1UEChMOQ1JZUFRPLVBSTyBMTEMxITAfBgNVBAMTGENSWVBUTy1QUk8gVGVzdCBDZW50ZXIgMgITEgAqm6mGL8arZdjnlwAAACqbqTAMBggqhQMHAQEBAQUABEB72moJL9jfVZOFn352Iqah//asg5OrGhGQuACSjVScQReIxB+bpP+v8oE2E0xiTyPWwi/L1UrQxmp3lqjnvn8cAAAAAAAA";
    private static final String signature_vtb = "MIAGCSqGSIb3DQEHAqCAMIACAQExDDAKBgYqhQMCAgkFADCABgkqhkiG9w0BBwEAAKCCCDQwgggwMIIH36ADAgECAhEBcgsBVlAAcKPoEdhcwGuLtTAIBgYqhQMCAgMwggFGMRgwFgYFKoUDZAESDTEyMzQ1Njc4OTAxMjMxGjAYBggqhQMDgQMBARIMMDAxMjM0NTY3ODkwMSkwJwYDVQQJDCDQodGD0YnQtdCy0YHQutC40Lkg0LLQsNC7INC0LiAyNjEXMBUGCSqGSIb3DQEJARYIY2FAcnQucnUxCzAJBgNVBAYTAlJVMRgwFgYDVQQIDA83NyDQnNC+0YHQutCy0LAxFTATBgNVBAcMDNCc0L7RgdC60LLQsDEkMCIGA1UECgwb0J7QkNCeINCg0L7RgdGC0LXQu9C10LrQvtC8MTAwLgYDVQQLDCfQo9C00L7RgdGC0L7QstC10YDRj9GO0YnQuNC5INGG0LXQvdGC0YAxNDAyBgNVBAMMK9Ci0LXRgdGC0L7QstGL0Lkg0KPQpiDQoNCi0JogKNCg0KLQm9Cw0LHRgSkwHhcNMTgwNTIxMDkxNDEyWhcNMTkwNTIxMDkyNDEyWjCCAWMxJDAiBgkqhkiG9w0BCQEWFUVzaWFUZXN0MDA0QHlhbmRleC5ydTEaMBgGCCqFAwOBAwEBEgwwMDEyMzQ1Njc4OTAxFjAUBgUqhQNkAxILMDAwMDAwNjAwMDQxGDAWBgUqhQNkARINMTIzNDU2Nzg5MDEyMzEbMBkGA1UEDAwS0KHQvtGC0YDRg9C00L3QuNC6MSYwJAYDVQQKDB3QntCQ0J4i0YLQtdGB0YLQvtCy0YvQuSDQrtCbIjEVMBMGA1UEBwwM0JzQvtGB0LrQstCwMRgwFgYDVQQIDA83NyDQnNC+0YHQutCy0LAxCzAJBgNVBAYTAlJVMSYwJAYDVQQqDB3QmNC80Y8wMDQg0J7RgtGH0LXRgdGC0LLQvjAwNDEaMBgGA1UEBAwR0KTQsNC80LjQu9C40Y8wMDQxJjAkBgNVBAMMHdCe0JDQniLRgtC10YHRgtC+0LLRi9C5INCu0JsiMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQG4Yh74RiKrk9N82P++cZNx1DYz+zFvU0r3TyU9dK3noIqY6yDkWJtAe+dNVEFaIzQ61yVnq7e0Oo1LCFWlEvWujggSDMIIEfzAOBgNVHQ8BAf8EBAMCBPAwHQYDVR0OBBYEFMlBolHLCUhOXPUmgd0fTqgJGZsKMIIBiAYDVR0jBIIBfzCCAXuAFD7vGT8PuXmw8eYpIaPkuZW5pe6QoYIBTqSCAUowggFGMRgwFgYFKoUDZAESDTEyMzQ1Njc4OTAxMjMxGjAYBggqhQMDgQMBARIMMDAxMjM0NTY3ODkwMSkwJwYDVQQJDCDQodGD0YnQtdCy0YHQutC40Lkg0LLQsNC7INC0LiAyNjEXMBUGCSqGSIb3DQEJARYIY2FAcnQucnUxCzAJBgNVBAYTAlJVMRgwFgYDVQQIDA83NyDQnNC+0YHQutCy0LAxFTATBgNVBAcMDNCc0L7RgdC60LLQsDEkMCIGA1UECgwb0J7QkNCeINCg0L7RgdGC0LXQu9C10LrQvtC8MTAwLgYDVQQLDCfQo9C00L7RgdGC0L7QstC10YDRj9GO0YnQuNC5INGG0LXQvdGC0YAxNDAyBgNVBAMMK9Ci0LXRgdGC0L7QstGL0Lkg0KPQpiDQoNCi0JogKNCg0KLQm9Cw0LHRgSmCEQFyCwFWUAC5s+cRzzq+NHegMB0GA1UdJQQWMBQGCCsGAQUFBwMCBggrBgEFBQcDBDAnBgkrBgEEAYI3FQoEGjAYMAoGCCsGAQUFBwMCMAoGCCsGAQUFBwMEMB0GA1UdIAQWMBQwCAYGKoUDZHEBMAgGBiqFA2RxAjArBgNVHRAEJDAigA8yMDE4MDUyMTA5MTQxMlqBDzIwMTkwNTIxMDkxNDEyWjCCATQGBSqFA2RwBIIBKTCCASUMKyLQmtGA0LjQv9GC0L7Qn9GA0L4gQ1NQIiAo0LLQtdGA0YHQuNGPIDMuOSkMLCLQmtGA0LjQv9GC0L7Qn9GA0L4g0KPQpiIgKNCy0LXRgNGB0LjQuCAyLjApDGPQodC10YDRgtC40YTQuNC60LDRgiDRgdC+0L7RgtCy0LXRgtGB0YLQstC40Y8g0KTQodCRINCg0L7RgdGB0LjQuCDihJYg0KHQpC8xMjQtMjUzOSDQvtGCIDE1LjAxLjIwMTUMY9Ch0LXRgNGC0LjRhNC40LrQsNGCINGB0L7QvtGC0LLQtdGC0YHRgtCy0LjRjyDQpNCh0JEg0KDQvtGB0YHQuNC4IOKEliDQodCkLzEyOC0yODgxINC+0YIgMTIuMDQuMjAxNjA2BgUqhQNkbwQtDCsi0JrRgNC40L/RgtC+0J/RgNC+IENTUCIgKNCy0LXRgNGB0LjRjyAzLjkpMGUGA1UdHwReMFwwWqBYoFaGVGh0dHA6Ly9jZXJ0ZW5yb2xsLnRlc3QuZ29zdXNsdWdpLnJ1L2NkcC8zZWVmMTkzZjBmYjk3OWIwZjFlNjI5MjFhM2U0Yjk5NWI5YTVlZTkwLmNybDBXBggrBgEFBQcBAQRLMEkwRwYIKwYBBQUHMAKGO2h0dHA6Ly9jZXJ0ZW5yb2xsLnRlc3QuZ29zdXNsdWdpLnJ1L2NkcC90ZXN0X2NhX3J0bGFiczIuY2VyMAgGBiqFAwICAwNBAKk9iZbzHpt5vd0FcBe8gN3btuRv0PPQqQxBFcjm3sp5ut1+F2tG6HhBsvFC8lg0kw/0WXPw8fmKyyXrZO5y7/4xggPlMIID4QIBATCCAV0wggFGMRgwFgYFKoUDZAESDTEyMzQ1Njc4OTAxMjMxGjAYBggqhQMDgQMBARIMMDAxMjM0NTY3ODkwMSkwJwYDVQQJDCDQodGD0YnQtdCy0YHQutC40Lkg0LLQsNC7INC0LiAyNjEXMBUGCSqGSIb3DQEJARYIY2FAcnQucnUxCzAJBgNVBAYTAlJVMRgwFgYDVQQIDA83NyDQnNC+0YHQutCy0LAxFTATBgNVBAcMDNCc0L7RgdC60LLQsDEkMCIGA1UECgwb0J7QkNCeINCg0L7RgdGC0LXQu9C10LrQvtC8MTAwLgYDVQQLDCfQo9C00L7RgdGC0L7QstC10YDRj9GO0YnQuNC5INGG0LXQvdGC0YAxNDAyBgNVBAMMK9Ci0LXRgdGC0L7QstGL0Lkg0KPQpiDQoNCi0JogKNCg0KLQm9Cw0LHRgSkCEQFyCwFWUABwo+gR2FzAa4u1MAoGBiqFAwICCQUAoIICHzAYBgkqhkiG9w0BCQMxCwYJKoZIhvcNAQcBMBwGCSqGSIb3DQEJBTEPFw0xODA5MjExNDM0MzRaMC8GCSqGSIb3DQEJBDEiBCDasS+938vIgW4DTym33fIJsNOikhleYy4zShTKI8eLXjCCAbIGCyqGSIb3DQEJEAIvMYIBoTCCAZ0wggGZMIIBlTAIBgYqhQMCAgkEILR+W4smLtHs+fO4DERYYTvy97V0nQs4TRSauHwEirdjMIIBZTCCAU6kggFKMIIBRjEYMBYGBSqFA2QBEg0xMjM0NTY3ODkwMTIzMRowGAYIKoUDA4EDAQESDDAwMTIzNDU2Nzg5MDEpMCcGA1UECQwg0KHRg9GJ0LXQstGB0LrQuNC5INCy0LDQuyDQtC4gMjYxFzAVBgkqhkiG9w0BCQEWCGNhQHJ0LnJ1MQswCQYDVQQGEwJSVTEYMBYGA1UECAwPNzcg0JzQvtGB0LrQstCwMRUwEwYDVQQHDAzQnNC+0YHQutCy0LAxJDAiBgNVBAoMG9Ce0JDQniDQoNC+0YHRgtC10LvQtdC60L7QvDEwMC4GA1UECwwn0KPQtNC+0YHRgtC+0LLQtdGA0Y/RjtGJ0LjQuSDRhtC10L3RgtGAMTQwMgYDVQQDDCvQotC10YHRgtC+0LLRi9C5INCj0KYg0KDQotCaICjQoNCi0JvQsNCx0YEpAhEBcgsBVlAAcKPoEdhcwGuLtTAKBgYqhQMCAhMFAARAC7+34uJnRZoiNtvavoBe4bAEnm4R4655WsDvzC8imalYm4UJDkJ+YlI6QEZ0ImZPUGxjmNYvtH6+rtjXXQ5pBgAAAAAAAA==";
    private static final String signature_goz = "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";
    private static final String signature_mmvb  = "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";
    private static final String document_tek = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPEZ1bmRzSG9sZFJxIHhtbG5zPSJodHRwOi8vd3d3LnNiZXJiYW5rLnJ1L2Vkby9vZXAvZWRvLW9lcC1wcm9jIj4KICAgIDxNc2dJRD43YzNjMDc5NTVjYzE0NGMzOGIxMTE4YTk3OTU3MmQ3NTwvTXNnSUQ+CiAgICA8TXNnVG0+MjAxOC0wOS0yNFQxMzoyOTo0MC4zNzkrMDM6MDA8L01zZ1RtPgogICAgPE9wZXJhdG9yTmFtZT5FVFBfVEVLVE9SRzwvT3BlcmF0b3JOYW1lPgogICAgPEFwcElEPmJucFVjSFpwV213PTwvQXBwSUQ+CiAgICA8QmFua0lEPlZUQjwvQmFua0lEPgogICAgPEVudHJ5Q2xvc2VUbT4yMDE4LTA5LTIyVDA5OjMwOjQ3LjArMDM6MDA8L0VudHJ5Q2xvc2VUbT4KICAgIDxOYW1lPtCQ0LPRgNC+0YLQvtGA0LMg0J7QntCePC9OYW1lPgogICAgPElOTj4zODE0MjE1MDAwPC9JTk4+CiAgICA8S1BQPjc4NDEwMTAwMTwvS1BQPgogICAgPEFjY291bnQ+NDA3MDI4MTAxMDAwMDAwMDI2MDA8L0FjY291bnQ+CiAgICA8QW1vdW50VG9Ib2xkeDEwMD4xMTExMTE8L0Ftb3VudFRvSG9sZHgxMDA+CjwvRnVuZHNIb2xkUnE+";
    private static final String signature_tek = "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";



    public static void main(String[] args) {

        boolean result = validateSignature(document_goz, signature_goz, null);

    }

    /********************************************************************************************************************
    ****************************** Тестируемый функционал *************************************************************
    ********************************************************************************************************************/

    static boolean validateSignature(String document, String signature, Certificate[]  certificates){
        boolean verifyresult = false;
        try {
            Utils.debugg("Попытка валидации подписи:");
            //начальная инициализация
            byte[] docbytes = Base64.decode(document);
            final Decoder decoder = new Decoder();
            final byte[] signbytes = decoder.decodeBuffer(signature);
            final Asn1BerDecodeBuffer asnBuf = new  Asn1BerDecodeBuffer(signbytes);
            final ContentInfo ci = new ContentInfo();
            ci.decode(asnBuf);
            final SignedData cms = (SignedData) ci.content;

            //проверка сертификатов
            X509Certificate certificate = null;
            final Certificate[] signatureCerts = SignatureUtils.readSignarureCertificates(ci);
            if (signatureCerts.length == 0){
                throw new Exception("Сертификаты в подписи не найдены!");
            }
            //если передан набор внешних сертификатов - верифицируем по ним
            if (certificates != null && certificates.length > 0) {
                Utils.debugg("для валидации использованы внешние сертификаты ("+certificates.length+")");
                for (int i = 0; i < certificates.length; i++)
                {
                    final X509Certificate cert = (X509Certificate) certificates[i];
                    for (int j = 0; j < cms.signerInfos.elements.length; j++)
                    {
                        try{
                            verifyresult = VerifyByCert(docbytes, cms.signerInfos.elements[j], cert);
                            if (verifyresult) {break;}
                        }
                        catch (Exception e){
                            Utils.debugg("ошибка валидации по сертификату ["+cert.getSubjectDN()+"]\n ==>", e);
                        }
                    } // for
                } // for
            }
            //иначе пробуем верифицировать по каждому из найденных в подписи
            else{
               Utils.debugg("для валидации использованы внутренние сертификаты ("+signatureCerts.length+")");
               for (int i = 0; i < signatureCerts.length; i++)
               {
                   final X509Certificate cert = (X509Certificate)signatureCerts[i];
                   try{
                       verifyresult = VerifyByCert(docbytes, cms.signerInfos.elements[i], cert);
                       if (verifyresult) {break;}
                   }
                   catch (Exception e){
                       Utils.debugg("ошибка валидации по сертификату ["+cert.getSubjectDN()+"]\n ==>", e);
                   }
               }
            }
        }
        catch (Exception e)
        {
            Utils.debugg("Произошли ошибки валидации ==>", e.fillInStackTrace());
        }
        //конечная обработка результатов
        if (verifyresult) {
            Utils.debugg("Валидация подписи прошла успешно!");
        }
        else {
            Utils.debugg("Не удалось валидировать подпись!");
        }
        return verifyresult;
    }

    static boolean VerifyByCert(byte[] docbytes, SignerInfo info, X509Certificate certificate) throws Exception
    {
        //дальнейшая проверка по соответствующему сертификату
        int[] digestAlgorithm = info.digestAlgorithm.algorithm.value;
        int[] keyAlgorithm = info.signatureAlgorithm.algorithm.value;
        final byte[] data;
        if (info.signedAttrs == null) {
            //аттрибуты подписи не присутствуют, верификация по документу
            Utils.debugg("валидация проводится по ==> документу");
            data = docbytes;
        }
        else {
            //присутствуют аттрибуты подписи (SignedAttr)
            Utils.debugg("валидация проводится по ==> атрибутам");
            final Attribute[] signAttrElem = info.signedAttrs.elements;

            //проверка аттрибута message-digest
            final Asn1ObjectIdentifier messageDigestOid = new Asn1ObjectIdentifier(
                    (new OID(SignatureUtils.STR_CMS_OID_DIGEST_ATTR)).value);

            Attribute messageDigestAttr = null;

            for (int r = 0; r < signAttrElem.length; r++) {
                final Asn1ObjectIdentifier oid = signAttrElem[r].type;
                if (oid.equals(messageDigestOid)) {
                    messageDigestAttr = signAttrElem[r];
                } // if
            } // for

            if (messageDigestAttr == null) {
                throw new Exception("message-digest attribute not present");
            } // if

            final Asn1Type open = messageDigestAttr.values.elements[0];
            final Asn1OctetString hash = (Asn1OctetString) open;
            final byte[] md = hash.value;

            //вычисление messageDigest
            final byte[] dm = SignatureUtils.digestm(docbytes, SignatureUtils.getOIDString(digestAlgorithm));

            if (!Array.toHexString(dm).equals(Array.toHexString(md))) {
                throw new Exception("ошибка верификации хэша документа!");
            } // if

            //верификация по атрибутам!
            final Asn1BerEncodeBuffer encBufSignedAttr = new Asn1BerEncodeBuffer();
            info.signedAttrs.needSortSignedAttributes = false;
            info.signedAttrs.encode(encBufSignedAttr);
            data = encBufSignedAttr.getMsgCopy();
        }
        //окончательная проверка
        final byte[] sign = info.signature.value;
        String signAlgorithm = SignatureUtils.getSignatureAlgorithm(digestAlgorithm, keyAlgorithm);
        Utils.debugg("алгоритм шифрования ==>", signAlgorithm);

        final Signature signobj = Signature.getInstance(signAlgorithm, JCP.PROVIDER_NAME);
        signobj.initVerify(certificate);
        signobj.update(data);

        return signobj.verify(sign);
    }





}